package com.meowbox.fourpillars

import com.meowbox.util.Loop
import com.meowbox.util.Loopable

enum class Branch(val nativeStem: Stem) : Loopable<Branch> {
    Rat(Stem.YangWater), // 0, 1
    Ox(Stem.YinEarth), // 2, 3
    Tiger(Stem.YangWood), // 4, 5
    Rabbit(Stem.YinWood), // 6, 7
    Dragon(Stem.YangEarth), // 8, 9
    Snake(Stem.YinFire), // 10, 11
    Horse(Stem.YangFire), // 12, 13
    Goat(Stem.YinEarth), // 14, 15
    Monkey(Stem.YangMetal), // 16, 17
    Rooster(Stem.YinMetal), // 18, 19
    Dog(Stem.YangEarth), // 20, 21
    Pig(Stem.YinWater); // 22, 23

    companion object : Loop<Branch>(Branch.values())

    override val all: Array<Branch>
        get() = Branch.all

    override fun advance(num: Int): Branch {
        return Branch.num(num + ordinal)
    }
}
