package com.meowbox.progressions.datastore

import com.meowbox.progressions.states.ApplicationState
import org.rekotlin.Store
import org.rekotlinrouter.RouteElementIdentifier
import org.rekotlinrouter.SetRouteAction

fun Store<ApplicationState>.route(vararg routes: RouteElementIdentifier) =
    dispatch(SetRouteAction(arrayListOf(*routes)))
