package com.meowbox.fourpillars

data class House(open val palace: Palace, open val branch: Branch) : Comparable<House> {
    override fun compareTo(other: House): Int {
        return branch.compareTo(other.branch)
    }

    constructor(starPlacement: Branch, mingLocation: Branch) :
            this(Palace.num(mingLocation - starPlacement), starPlacement)
}

