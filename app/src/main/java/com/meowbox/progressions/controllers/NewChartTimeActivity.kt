package com.meowbox.progressions.controllers

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.meowbox.DateTime
import com.meowbox.fourpillars.Chart
import com.meowbox.fourpillars.Star
import com.meowbox.progressions.R
import com.meowbox.progressions.datastore.CurrentChart
import com.meowbox.progressions.datastore.NewChart
import com.meowbox.progressions.datastore.route
import com.meowbox.progressions.db.Db
import com.meowbox.progressions.db.loadChart
import com.meowbox.progressions.db.toSolarDate
import com.meowbox.progressions.routes.ChartListRoutable
import com.meowbox.progressions.routes.ViewChartRoutable
import com.meowbox.progressions.store
import kotlinx.android.synthetic.main.activity_new_chart_time.*
import org.rekotlin.StoreSubscriber

class NewChartTimeActivity : AppCompatActivity(), StoreSubscriber<NewChart.State> {
    private var dob: DateTime = DateTime(0)

    private var name: String = ""

    override fun newState(state: NewChart.State) {
        this.dob = state.dob
        this.name = state.name
    }

    private fun Chart.findStarPalace(star: Star) = houses.filter { it.value.contains(star) }.keys.first().palace

    fun save(view: View) {
        dob =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                dob.copy(hourOfDay = dob_TimePicker.hour, minuteOfHour = dob_TimePicker.minute)
            else
                dob.copy(hourOfDay = dob_TimePicker.currentHour, minuteOfHour = dob_TimePicker.currentMinute)
        store.dispatch(NewChart.ChangeDobAction(dob))
        with(loadChart(dob)) {
            Db.instance.chartRecordQueries.insertOne(
                dob.toSolarDate(),
                name,
                dob,
                yearPillar.branch,
                yearPillar.stem,
                hourPillar.branch,
                hourPillar.stem,
                ming,
                findStarPalace(Star.ZiWei),
                findStarPalace(Star.TianFu)
            )
            val newRecord = Db.instance.chartRecordQueries.getLast().executeAsOne()
            store.dispatch(NewChart.InsertChartRecordAction(newRecord))
            store.dispatch(CurrentChart.SelectCurrentChartAction(newRecord, loadChart(newRecord.dob)))
            Log.d(javaClass.simpleName, "Routing to ${ChartListRoutable.id}")
            store.route(ChartListRoutable.id, ViewChartRoutable.id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_chart_time)
        store.subscribe(this) { subscription ->
            subscription.select { it.newChart }.skipRepeats { _, new -> new.dob == dob }
        }

        next_Button.setOnClickListener(this::save)
    }

    override fun onDestroy() {
        super.onDestroy()
        store.unsubscribe(this)
    }
}
