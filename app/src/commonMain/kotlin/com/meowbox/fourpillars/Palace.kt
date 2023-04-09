package com.meowbox.fourpillars

import com.meowbox.util.Loop

enum class Palace {
    Ming, Youth, Partner, Children, Wealth, Health, Career, Assistants, Superiors, Property, Pleasure, Ancestors;

    companion object : Loop<Palace>(Palace.values())
}
