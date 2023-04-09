package com.meowbox.progressions.routes

import android.content.Context
import org.rekotlinrouter.Routable
import org.rekotlinrouter.RouteElementIdentifier
import org.rekotlinrouter.RoutingCompletionHandler

class NewChartTimeRoutable(context: Context) : Routable {
    companion object {
        const val id: RouteElementIdentifier = "newChartTime"
    }

    override fun popRouteSegment(
        routeElementIdentifier: RouteElementIdentifier,
        animated: Boolean,
        completionHandler: RoutingCompletionHandler
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pushRouteSegment(
        routeElementIdentifier: RouteElementIdentifier,
        animated: Boolean,
        completionHandler: RoutingCompletionHandler
    ): Routable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeRouteSegment(
        from: RouteElementIdentifier,
        to: RouteElementIdentifier,
        animated: Boolean,
        completionHandler: RoutingCompletionHandler
    ): Routable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}