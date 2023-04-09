package com.meowbox.progressions.controllers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.meowbox.progressions.R
import com.meowbox.progressions.datastore.NewChart
import com.meowbox.progressions.datastore.route
import com.meowbox.progressions.routes.ChartListRoutable
import com.meowbox.progressions.routes.NewChartYearRoutable
import com.meowbox.progressions.store
import kotlinx.android.synthetic.main.activity_new_chart.*
import org.rekotlin.StoreSubscriber

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

class NewChartActivity : AppCompatActivity(), StoreSubscriber<NewChart.State> {

    override fun newState(state: NewChart.State) {
        personName_PlainText.setText(state.name)
        personName_PlainText.setSelection(state.name.length)
    }

    private fun onEditorActionListener(view: TextView, actionId: Int, event: KeyEvent?) = true.also {
        Log.e("NewChartActivity", "shit head")
        if (actionId == EditorInfo.IME_ACTION_NEXT || event?.keyCode == KeyEvent.KEYCODE_ENTER)
            store.route(ChartListRoutable.id, NewChartYearRoutable.id)
    }

    override fun onDestroy() {
        super.onDestroy()
        store.unsubscribe(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_chart)
        personName_PlainText.setOnEditorActionListener(::onEditorActionListener)
        personName_PlainText.afterTextChanged { name ->
            store.dispatch(NewChart.ChangeNameAction(name))
        }

        store.subscribe(this) { subscription ->
            subscription.select { it.newChart }.skipRepeats { _, new ->
                new.name == personName_PlainText.text.toString()
            }
        }
    }
}
