package com.meowbox

private fun Int.pow(p: Int): Int =
    if (p == 1)
        this
    else
        this * this.pow(p - 1)

/*********************************************************
 * Bits 00..07 (8) : 0       : Year value 0..199 in years from 1900..2099
 * Bits 08..11 (4) : 256     : Month value 1..12
 * Bits 12..16 (5) : 4096    : Day of month 1..31
 * Bits 17..21 (5) : 131072  : Hour of day 0..23
 * Bits 22..27 (6) : 4194304 : Minute of hour 0..59
 * Bits 28..28 (1) :
 *
 *
 */
inline class DateTime(val id: Int) {

    constructor(year: Int, monthOfYear: Int, dayOfMonth: Int, hourOfDay: Int, minuteOfHour: Int, isDst: Boolean = false)
            : this(
        (year - firstYear) +
                monthOfYear * monthMultiplier +
                dayOfMonth * dayMultiplier +
                hourOfDay * hourMultiplier +
                minuteOfHour * minuteMultiplier +
                if (isDst) dstMultiplier else 0
    )

    val year: Int get() = firstYear + id.rem(monthMultiplier)
    val monthOfYear: Int get() = id.rem(dayMultiplier) / monthMultiplier
    val dayOfMonth: Int get() = id.rem(hourMultiplier) / dayMultiplier
    val hourOfDay: Int get() = id.rem(minuteMultiplier) / hourMultiplier
    val minuteOfHour: Int get() = id.rem(dstMultiplier) / minuteMultiplier
    val isDst: Boolean get() = id / dstMultiplier == 1

    fun copy(
        year: Int = this.year, monthOfYear: Int = this.monthOfYear, dayOfMonth: Int = this.dayOfMonth,
        hourOfDay: Int = this.hourOfDay,
        minuteOfHour: Int = this.minuteOfHour
    ) = DateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour)

    override fun toString() = "DateTime($year/$monthOfYear/$dayOfMonth $hourOfDay:$minuteOfHour)"

    companion object {
        private const val firstYear = 1900
        private const val yearBits = 8
        private const val monthBits = 4
        private const val dayBits = 5
        private const val hourBits = 5
        private const val minuteBits = 6
        private const val yearMultiplier = 1
        private val monthMultiplier = yearMultiplier * 2.pow(yearBits)
        private val dayMultiplier = monthMultiplier * 2.pow(monthBits)
        private val hourMultiplier = dayMultiplier * 2.pow(dayBits)
        private val minuteMultiplier = hourMultiplier * 2.pow(hourBits)
        private val dstMultiplier = minuteMultiplier * 2.pow(minuteBits)
    }

}