package com.meowbox.progressions.views

import android.content.Context
import android.widget.TextView
import com.meowbox.fourpillars.Branch
import com.meowbox.fourpillars.Star


class StarView(star: Star, branch: Branch, context: Context?) : TextView(context) {
    //    private val stuff = "❗️⬇️⬆️〰️⇧🔺"
    private val exalted: Boolean = branch in star.exalted
    private val debilitated: Boolean = branch in star.debilitated
    private val exaltationString = if (exalted) "▲" else if (debilitated) "▼" else ""
    private val textSize = when (star.Rank) {
        1 -> 16
        2 -> 13
        3 -> 11
        4 -> 10
        else -> 8
    }.let { if (exalted) it + 2 else it }

    init {
        text = star.english + exaltationString

        setTextSize(textSize.toFloat())


    }
}