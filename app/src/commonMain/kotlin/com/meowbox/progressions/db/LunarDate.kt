package com.meowbox.progressions.db

import com.meowbox.fourpillars.Branch
import com.meowbox.fourpillars.Pillar
import com.meowbox.fourpillars.Stem

inline class LunarDate(val id: Short) {

    val dayOfMonth: Int
        get() = id / dayOfMonthBit

    val yearPillar: Pillar get() = Pillar.all[yearInEpoch - 1]

    val monthPillar: Pillar
        get() = Pillar(
            Stem.all[(2 * yearInEpoch + monthNumber - 1) % 10],
            Branch.all[(monthNumber + 1) % 12]
        )

    val monthNumber: Int
        get() = id.rem(dayOfMonthBit) / monthNumberBit

    val yearInEpoch: Int
        get() = id.rem(monthNumberBit)

    companion object {
        private const val monthNumberBit: Short = 64
        private const val dayOfMonthBit: Short = 1024
        private const val initialDayPillarNum = 10
    }
}
