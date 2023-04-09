package com.meowbox.progressions.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.meowbox.progressions.R
import com.meowbox.progressions.datastore.CurrentChart
import com.meowbox.progressions.db.Db
import com.meowbox.progressions.db.toLocalDateTime
import com.meowbox.progressions.store
import kotlinx.android.synthetic.main.view_chart_center.view.*
import org.rekotlin.StoreSubscriber

private val months = arrayOf(
    "January", "February", "March", "April", "May", "June", "July", "August",
    "September", "October", "November", "December"
)

private fun Int.ordinalize(): String =
    if (this in 10..20)
        "${this}th"
    else when (rem(10)) {
        1 -> "${this}st"
        2 -> "${this}nd"
        3 -> "${this}rd"
        else -> "${this}th"
    }

class ChartCenterView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs),
    StoreSubscriber<CurrentChart.State> {

    override fun newState(state: CurrentChart.State) {
        val chart = state.chart
        val chartRecord = state.chartRecord
        personName_textView.text = chartRecord.name
        with(chartRecord.dob.toLocalDateTime()) {
            dob_textView.text = "$year ${months[monthOfYear - 1]} " +
                    "${dayOfMonth.toString().padStart(2, '0')}, " +
                    "${hourOfDay.rem(12)}:${minuteOfHour.toString().padStart(2, '0')} " +
                    if (hourOfDay >= 12) "pm" else "am"
        }
        with(Db.instance.ephemerisPointQueries.getBySolarDate(chartRecord.ephemerisPointId).executeAsOne().lunarDate) {
            lunarDate_textView.text = "${monthNumber.ordinalize()} Month, ${dayOfMonth.ordinalize()} Day"
        }
        innerElement_textView.text = "Inner Element: ${state.chart.innerElement.name}"
        with(chart.yearPillar) {
            yearBranch_textView.text = branch.name
            yearStem_textView.text = stem.element.name
        }
        with(chart.monthPillar) {
            monthBranch_textView.text = branch.name
            monthStem_textView.text = stem.element.name
        }
        with(chart.dayPillar) {
            dayBranch_textView.text = branch.name
            dayStem_textView.text = stem.element.name
        }
        with(chart.hourPillar) {
            hourBranch_textView.text = branch.name
            hourStem_textView.text = stem.element.name
        }
    }

    /**********************************************************
     * Loads the branch name from the style attributes given to this view
     * in the chart layout.
     *
     */
    init {
        View.inflate(context, R.layout.view_chart_center, this)

        store.subscribe(this) { subscription ->
            subscription.select { state ->
                state.currentChart!!
            }.skipRepeats()
        }
    }
}
