package com.meowbox.fourpillars

import com.meowbox.util.Loop
import com.meowbox.util.Loopable

data class Pillar(val stem: Stem, val branch: Branch) : Loopable<Pillar> {
    override val ordinal: Int
        get() {
            var diff = ((stem.ordinal - branch.ordinal) / 2).rem(6)
            if (diff < 0) diff += 6
            return diff * 10 + stem.ordinal
        }

    companion object : Loop<Pillar>(Array(60) { Pillar(Stem.all[it % 10], Branch.all[it % 12]) })

    override val all: Array<Pillar>
        get() = Pillar.all

    override fun advance(num: Int): Pillar {
        return Pillar.num(num + ordinal)
    }
}
