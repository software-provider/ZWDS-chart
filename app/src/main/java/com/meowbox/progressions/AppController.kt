package com.meowbox.progressions

import android.app.Application
import android.content.Context
import android.util.Log
import com.meowbox.DateTime
import com.meowbox.progressions.datastore.CurrentChart
import com.meowbox.progressions.datastore.NewChart
import com.meowbox.progressions.datastore.RewindableNavigationState
import com.meowbox.progressions.datastore.Search
import com.meowbox.progressions.db.ChartData
import com.meowbox.progressions.db.Db
import com.meowbox.progressions.db.loadDatabase
import com.meowbox.progressions.db.toSolarDate
import com.meowbox.progressions.routes.RootRoutable
import com.meowbox.progressions.states.ApplicationState
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.rekotlin.Action
import org.rekotlin.Middleware
import org.rekotlin.Reducer
import org.rekotlin.Store
import org.rekotlinrouter.NavigationReducer
import org.rekotlinrouter.Router
import org.rekotlinrouter.SetRouteAction

typealias ComponentReducer = (Action, ApplicationState) -> ApplicationState


private val applicationReducer: Reducer<ApplicationState> = { action, oldState ->
    var oldState = oldState ?: ApplicationState(
        RewindableNavigationState.State(
            routingState = NavigationReducer.handleAction(action, null)
        )
    )
    oldState.copy(
        navigationState = RewindableNavigationState.reducer(action, oldState.navigationState),
        currentChart = CurrentChart.reducer(action, oldState.currentChart),
        search = Search.reducer(action, oldState.search),
        newChart = NewChart.reducer(action, oldState.newChart)
    ).also {
        when (action) {
            is CurrentChart.SelectCurrentChartAction -> Log.i("Reducer", "$it")
            is SetRouteAction -> Log.i("NavigationReducer", "${it.navigationState}")
            is RewindableNavigationState.NavigateBackAction -> Log.i("NavigationReducer", "${it.navigationState}")
            is NewChart.InsertChartRecordAction -> Log.i("Reducer", "Charts: ${it.search.searchResults}")
        }
    }
}

private fun Action.actionString(): String = javaClass.simpleName + when (this) {
    is SetRouteAction -> " $route"
    else -> ""
}

val logMiddleware: Middleware<ApplicationState> = { _, _ ->
    { next ->
        { action ->

            Log.d("ActionLogger", action.actionString())
            next(action)
        }
    }
}

val store =
    Store(
        applicationReducer,
        state = null,
        middleware = listOf(logMiddleware),
        automaticallySkipRepeats = true
    )

var router: Router<ApplicationState>? = null
    private set

private const val dbName = "progressions.sqlite3"
private const val ZERO = 0L
class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return
//        }
//        LeakCanary.install(this)
        loadDatabase(applicationContext, dbName)
        Db.dbSetup(AndroidSqliteDriver(Database.Schema, applicationContext, dbName))
        val count = Db.instance.chartRecordQueries.countMine().executeAsOne()
        if (count == ZERO) {
            DateTime(1978, 4, 7, 15, 30).also { dob ->
                ChartData.saveChart(dob.toSolarDate(), dob, "Tyler Gannon")
                Log.i("AppController", "Created chart for duder.")
            }
        } else Log.i("ApplicationController", "I have $count chart records.")

        instance = this

        router = Router(store, RootRoutable(applicationContext)) { subscription ->
            subscription.select { state -> state.navigationState.routingState }
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        Db.dbClear()
    }


    companion object {

        //Getting tag it will be used for displaying log and it is optional
        val TAG = AppController::class.java.simpleName


        //Creating class object
        //Public static method to get the instance of this class
        @get:Synchronized
        var instance: AppController? = null
            private set

        fun getAppController(context: Context): AppController {
            if (instance == null) {
                //Create instance
                instance = AppController()
            }

            return instance as AppController
        }
    }
}

