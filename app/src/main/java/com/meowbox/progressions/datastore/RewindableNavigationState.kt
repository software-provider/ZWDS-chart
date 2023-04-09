package com.meowbox.progressions.datastore

import android.util.Log
import org.rekotlin.Action
import org.rekotlinrouter.*

class RewindableNavigationState {
    data class State(
        val routingState: NavigationState,
        val history: List<NavigationState> = emptyList()
    )

    class NavigateBackAction : Action
    class NavigateUpAction(vararg val route: RouteElementIdentifier) : Action {
        val setRouteAction = SetRouteAction(Route(route.toMutableList()))
    }

    companion object {
        fun reducer(action: Action, state: State): State =
            state.routingState.copy().let { oldRoutingState ->
                state.copy(
                    routingState = when (action) {
                        is NavigateBackAction -> state.history.last().also {
                            Log.d("RewindableNavState", "$it")
                        }
                        is NavigateUpAction -> NavigationReducer.reduce(action.setRouteAction, state.routingState)
                        else -> NavigationReducer.reduce(action, state.routingState)
                    },
                    history = when (action) {
                        is SetRouteAction -> state.history.plus(oldRoutingState)
                        is NavigateUpAction -> with(state.history) {
                            val indexOfTargetRoute = this.indexOfFirst {
                                it.route.toList() == action.route.toList()
                            }
                            if (indexOfTargetRoute >= 0)
                                subList(0, indexOfTargetRoute)
                            else
                                emptyList()
                        }
                        is NavigateBackAction -> with(state.history) {
                            when (size) {
                                0 -> emptyList()
                                1 -> emptyList()
                                else -> subList(0, size - 2)
                            }
                        }
                        else -> state.history
                    }
                )
            }.apply {
                when (action) {
                    is SetRouteAction ->
                        Log.d(javaClass.simpleName, "SetRouteAction -- ${this.history}")
                    is NavigateBackAction ->
                        Log.d(javaClass.simpleName, "NavigateBackAction -- ${this.history}")
                }
            }
    }
}
