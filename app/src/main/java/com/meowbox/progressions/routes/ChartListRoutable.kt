package com.meowbox.progressions.routes

import android.content.Context
import android.util.Log
import org.rekotlinrouter.Routable
import org.rekotlinrouter.RouteElementIdentifier
import org.rekotlinrouter.RoutingCompletionHandler

class ChartListRoutable(val context: Context) : Routable {
    override fun changeRouteSegment(
        from: RouteElementIdentifier,
        to: RouteElementIdentifier,
        animated: Boolean,
        completionHandler: RoutingCompletionHandler
    ): Routable =
        when (from) {
            NewChartRoutable.id -> when (to) {
                NewChartYearRoutable.id -> RouteHelper.createNewChartYearRoutable(context)
                else -> TODO("not implemented, but FROM=$from and TO=$to")
            }
            NewChartYearRoutable.id -> when (to) {
                NewChartDobRoutable.id -> RouteHelper.createNewChartDobRoutable(context)
                NewChartRoutable.id -> RouteHelper.createNewChartRoutable(context)
                else -> TODO("not implemented, but FROM=$from and TO=$to")
            }
            NewChartDobRoutable.id -> when (to) {
                NewChartTimeRoutable.id -> RouteHelper.createNewChartTimeRoutable(context)
                NewChartYearRoutable.id -> RouteHelper.createNewChartYearRoutable(context)
                else -> TODO("not implemented, but FROM=$from and TO=$to")
            }
            NewChartTimeRoutable.id -> when (to) {
                ViewChartRoutable.id -> RouteHelper.createViewChartRoutable(context)
                NewChartDobRoutable.id -> RouteHelper.createNewChartDobRoutable(context)
                else -> TODO("not implemented, but FROM=$from and TO=$to")
            }
            else -> TODO("not implemented, but FROM=$from and TO=$to")
        }.also { Log.i("ChartList/changeSegment", "FROM=$from, TO=$to") }

    override fun popRouteSegment(
        routeElementIdentifier: RouteElementIdentifier,
        animated: Boolean,
        completionHandler: RoutingCompletionHandler
    ) {
        Log.d("ChartListRoutable", "popRouteSegment (routeElementIdentifier=$routeElementIdentifier)")
    }

    override fun pushRouteSegment(
        routeElementIdentifier: RouteElementIdentifier,
        animated: Boolean,
        completionHandler: RoutingCompletionHandler
    ): Routable {
        if (routeElementIdentifier == NewChartRoutable.id)
            return RouteHelper.createNewChartRoutable(context)
        if (routeElementIdentifier == ViewChartRoutable.id)
            return RouteHelper.createViewChartRoutable(context)
        TODO("Unrecognized route routeElementIdentifier=$routeElementIdentifier")
    }

    companion object {
        val id: RouteElementIdentifier = "chartList"
    }
}
