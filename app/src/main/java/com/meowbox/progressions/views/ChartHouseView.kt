package com.meowbox.progressions.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.meowbox.fourpillars.Branch
import com.meowbox.fourpillars.House
import com.meowbox.fourpillars.Star
import com.meowbox.progressions.R
import com.meowbox.progressions.store
import kotlinx.android.synthetic.main.layout_chart_house_view.view.*
import org.rekotlin.StoreSubscriber

class ChartHouseView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs),
    StoreSubscriber<Map.Entry<House, List<Star>>> {

    override fun newState(state: Map.Entry<House, List<Star>>) {
        house_text_view.text = state.key.palace.name
        branch_text_view.text = state.key.branch.name
        with(stars_layout) {
            removeAllViews()
            for (star in state.value)
                addView(StarView(star, state.key.branch, context).apply {
                    //                    text = star.english
                    id = star.ordinal

                })
        }
    }

    /**********************************************************
     * Loads the branch name from the style attributes given to this view
     * in the chart layout.
     *
     */
    init {
        View.inflate(context, R.layout.layout_chart_house_view, this)
        val branch = Branch.valueOf(tag.toString())

        store.subscribe(this) { subscription ->
            subscription.select { state ->
                state.currentChart!!.chart.houses.entries.first { it.key.branch == branch }
            }.skipRepeats()
        }
    }
}
