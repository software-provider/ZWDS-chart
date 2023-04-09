package com.meowbox.progressions.controllers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.meowbox.DateTime
import com.meowbox.progressions.R
import com.meowbox.progressions.datastore.NewChart
import com.meowbox.progressions.datastore.route
import com.meowbox.progressions.routes.ChartListRoutable
import com.meowbox.progressions.routes.NewChartTimeRoutable
import com.meowbox.progressions.store
import kotlinx.android.synthetic.main.activity_new_chart_dob.*
import org.rekotlin.StoreSubscriber

class NewChartDobActivity : AppCompatActivity(), StoreSubscriber<NewChart.State> {
    private var dob: DateTime = DateTime(0)

    override fun newState(state: NewChart.State) {
        dob = state.dob
        Log.i(this.javaClass.name, "Got state: ${state}")
        dob_DatePicker.updateDate(dob.year, dob.monthOfYear - 1, dob.dayOfMonth)
    }

    override fun onDestroy() {
        super.onDestroy()
        store.unsubscribe(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_chart_dob)

        next_Button.setOnClickListener {
            store.dispatch(
                NewChart.ChangeDobAction(
                    dob.copy(
                        year = dob_DatePicker.year,
                        monthOfYear = (dob_DatePicker.month + 1),
                        dayOfMonth = dob_DatePicker.dayOfMonth
                    ).also {
                        Log.i("NewChartDobActivity", "Change dob to $it")
                    }
                ))
            store.route(ChartListRoutable.id, NewChartTimeRoutable.id)
        }

        store.subscribe(this) { subscription ->
            subscription.select { it.newChart }.skipRepeats { _, new ->
                new.dob == dob
            }
        }
    }
}
