package com.meowbox.util

interface Loopable<T> {
    fun advance(num: Int): T
    operator fun plus(num: Int): T = advance(num)
    operator fun plus(loopable: Loopable<T>): Int = ordinal + loopable.ordinal
    operator fun unaryPlus(): T = advance(1)
    operator fun unaryMinus(): T = advance(-1)
    operator fun minus(num: Int): T = advance(-num)
    operator fun minus(loopable: Loopable<T>): Int = ordinal - loopable.ordinal
    val all: Array<T>
    val ordinal: Int
    private val count: Int get() = all.size
    val diametric: T get() = advance(count / 2)
    val next: T get() = advance(1)
    val prev: T get() = advance(count - 1)
}

