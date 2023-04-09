package com.meowbox.progressions.states

import com.meowbox.progressions.datastore.CurrentChart
import com.meowbox.progressions.datastore.NewChart
import com.meowbox.progressions.datastore.RewindableNavigationState
import com.meowbox.progressions.datastore.Search
import org.rekotlin.StateType

data class ApplicationState(
    var navigationState: RewindableNavigationState.State,
    val currentChart: CurrentChart.State? = null,
    val newChart: NewChart.State = NewChart.State(),
    val search: Search.State = Search.State()
) : StateType
