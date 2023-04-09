package com.meowbox.fourpillars

import com.meowbox.util.Loop
import com.meowbox.util.Loopable

enum class Stem(val element: Element) : Loopable<Stem> {
    YangWood(Element.Wood),
    YinWood(Element.Wood),
    YangFire(Element.Fire),
    YinFire(Element.Fire),
    YangEarth(Element.Earth),
    YinEarth(Element.Earth),
    YangMetal(Element.Metal),
    YinMetal(Element.Metal),
    YangWater(Element.Water),
    YinWater(Element.Water);

    companion object : Loop<Stem>(Stem.values())

    override val all: Array<Stem>
        get() = Stem.all

    override fun advance(num: Int): Stem {
        return Stem.num(num + ordinal)
    }
}
