package com.meowbox.progressions.routes

import android.content.Context
import org.rekotlinrouter.Routable
import org.rekotlinrouter.RouteElementIdentifier
import org.rekotlinrouter.RoutingCompletionHandler

class RootRoutable(val context: Context) : Routable {
    override fun popRouteSegment(
        routeElementIdentifier: RouteElementIdentifier,
        animated: Boolean,
        completionHandler: RoutingCompletionHandler
    ) {
    }

    override fun pushRouteSegment(
        routeElementIdentifier: RouteElementIdentifier,
        animated: Boolean,
        completionHandler: RoutingCompletionHandler
    ): Routable =

        when (routeElementIdentifier) {
            NewChartRoutable.id -> NewChartRoutable(context)
            ChartListRoutable.id -> ChartListRoutable(context)
            else -> ChartListRoutable(context)
        }

    override fun changeRouteSegment(
        from: RouteElementIdentifier,
        to: RouteElementIdentifier,
        animated: Boolean,
        completionHandler: RoutingCompletionHandler
    ): Routable {
        TODO("not implemented")
    }

    companion object {
        val id: RouteElementIdentifier = "root"
    }
}