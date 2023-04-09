package com.meowbox.util

fun <T> Iterable<T>.fancyJoin(joiner: String, empty: String = "", block: (T) -> String) =
    when (count()) {
        0 -> empty
        1 -> block(first())
        2 -> joinToString(" $joiner ")
        else -> {
            var returnVal = ""
            forEachIndexed { index, star ->
                returnVal += block(star) +
                        when (count() - index) {
                            1 -> ""
                            2 -> ", $joiner "
                            else -> ", "
                        }
            }
            returnVal
        }
    }