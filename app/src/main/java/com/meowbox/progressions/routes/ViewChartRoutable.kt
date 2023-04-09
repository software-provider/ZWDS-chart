package com.meowbox.progressions.routes

import android.content.Context
import com.meowbox.fourpillars.Branch
import org.rekotlinrouter.Routable
import org.rekotlinrouter.RouteElementIdentifier
import org.rekotlinrouter.RoutingCompletionHandler

class ViewChartRoutable(val context: Context) : Routable {
    override fun changeRouteSegment(
        from: RouteElementIdentifier,
        to: RouteElementIdentifier,
        animated: Boolean,
        completionHandler: RoutingCompletionHandler
    ): Routable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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
    ): Routable {
        if (routeElementIdentifier in Branch.all.map { it.name })
            return RouteHelper.createChartHouseDetailRoutable(context)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val id: RouteElementIdentifier = "viewChart"
    }
}