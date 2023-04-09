package com.meowbox.progressions.datastore

import com.meowbox.fourpillars.Branch
import com.meowbox.fourpillars.Chart
import com.meowbox.fourpillars.Palace
import com.meowbox.progressions.ChartRecord
import com.meowbox.progressions.StarComment
import org.rekotlin.Action

class CurrentChart {
    data class State(
        val chart: Chart,
        val chartRecord: ChartRecord,
        val progressedTo: Branch? = null,
        val selectedPalace: Palace? = null,
        val palaceComments: List<StarComment> = listOf()
    )

    class SelectCurrentChartAction(val chartRecord: ChartRecord, val chart: Chart) : Action {
        override fun toString() = "SelectCurrentChartAction(chartRecord = '$chartRecord', chart = '$chart')"
    }
    class SelectChartProgression(val progressTo: Branch?) : Action
    class SelectFocusPalaceAction(val palace: Palace) : Action
    class LoadStarCommentsAction(val starComments: List<StarComment>) : Action

    companion object {
        fun reducer(action: Action, state: State?) =
                when (action) {
                    is SelectCurrentChartAction ->
                        State(action.chart, action.chartRecord)
                    is SelectChartProgression ->
                        state!!.copy(progressedTo = action.progressTo)
                    is SelectFocusPalaceAction ->
                        state!!.copy(selectedPalace = action.palace)
                    is LoadStarCommentsAction ->
                        state!!.copy(palaceComments = action.starComments)
                    else -> state
                }
    }
}
