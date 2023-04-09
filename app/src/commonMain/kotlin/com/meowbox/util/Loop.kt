package com.meowbox.util

/**************************************************
 * Used on Enum values that
 */
open class Loop<T>(val all: Array<T>) {
    fun num(n: Int): T {
        val rem = n.rem(all.size)
        return all[if (rem < 0) rem + all.size else rem]
    }
}
