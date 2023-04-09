package com.meowbox.fourpillars

enum class Element {
    Wood, Fire, Earth, Metal, Water;

    companion object {
        val innerElements = arrayOf(
            arrayOf(Water, Fire, Earth, Wood, Metal),
            arrayOf(Fire, Earth, Wood, Metal, Water),
            arrayOf(Wood, Metal, Water, Fire, Earth),
            arrayOf(Earth, Wood, Metal, Water, Fire),
            arrayOf(Metal, Water, Fire, Earth, Wood),
            arrayOf(Fire, Earth, Wood, Metal, Water)
        )
        val elementScoreTable = arrayOf(
            Metal, Fire, Wood, Earth, Metal, Fire, Wood, Earth, Metal, Wood,
            Water, Earth, Fire, Wood, Water, Metal, Fire, Wood, Earth, Metal,
            Fire, Water, Earth, Metal, Wood, Water, Earth, Fire, Wood, Water
        )
    }
}

