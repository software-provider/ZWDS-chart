package com.meowbox.progressions.controllers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.meowbox.DateTime
import com.meowbox.progressions.R
import com.meowbox.progressions.datastore.NewChart
import com.meowbox.progressions.datastore.route
import com.meowbox.progressions.routes.ChartListRoutable
import com.meowbox.progressions.routes.NewChartDobRoutable
import com.meowbox.progressions.store
import kotlinx.android.synthetic.main.activity_new_chart_year.*
import org.rekotlin.StoreSubscriber

class NewChartYearActivity : AppCompatActivity(), StoreSubscriber<NewChart.State> {
    var dob: DateTime = DateTime(1970, 1, 1, 0, 0)
    override fun newState(state: NewChart.State) {
        year_EditText.setText(dob.year.toString())
        year_EditText.setSelection(dob.year.toString().length)
    }

    private fun onEditorActionListener(view: TextView, actionId: Int, event: KeyEvent?) = true.also {
        if (actionId == EditorInfo.IME_ACTION_NEXT || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
            Log.i(this.javaClass.name, "NEXT clicked for ${year_EditText.text}.")
            store.dispatch(NewChart.ChangeDobAction(dob = dob.copy(year = "${year_EditText.text}".toInt())))
            store.route(ChartListRoutable.id, NewChartDobRoutable.id)
        }
    }

    private fun storeDob(year: Int) {
        dob = dob.copy(year = year)
        store.dispatch(NewChart.ChangeDobAction(dob = dob))
    }

    override fun onDestroy() {
        super.onDestroy()
        store.unsubscribe(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_chart_year)

        year_EditText.setOnEditorActionListener { _, actionId, event ->
            true.also {
                if (actionId == EditorInfo.IME_ACTION_NEXT || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                    Log.i(
                        this.javaClass.name,
                        "NEXT clicked for ${year_EditText.text}.  actionid=$actionId, keycode=${event?.keyCode}"
                    )
                    year_EditText.text.toString().apply {
                        if (length == 4)
                            storeDob(toInt())
                    }

                    store.route(ChartListRoutable.id, NewChartDobRoutable.id)
                }
            }
        }


//        year_EditText.afterTextChanged { text ->
//            if (text.length == 4)
//                storeDob(text.toInt())
//        }


        store.subscribe(this) { subscription ->
            subscription.select { it.newChart }.skipRepeats { _, new ->
                new.dob == dob
            }
        }
    }
}
