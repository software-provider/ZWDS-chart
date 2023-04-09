package com.meowbox.fourpillars

class Chart(
    val yearPillar: Pillar,
    val monthPillar: Pillar,
    val dayPillar: Pillar,
    val hourPillar: Pillar,
    val dayOfMonth: Int,
    val progressTo: Branch? = null
) {
    val ming = Branch.num(monthPillar.branch.ordinal - hourPillar.branch.ordinal)

    val innerElement = Element.innerElements[(ming.ordinal) / 2][yearPillar.stem.ordinal % 5]
    val elementScores: ElementScores
        get() {
            val scores = ElementScores()
            for (pillar in arrayOf(yearPillar, monthPillar, dayPillar, hourPillar)) {
                scores.score(pillar.stem.element)
                scores.score(pillar.branch.nativeStem.element)
                scores.score(Element.elementScoreTable[pillar.ordinal / 2])
            }
            return scores
        }


    val houses = Star.all.groupBy { House(it.findIn(this), progressTo ?: ming) }.let { initialMap ->
        var newMap = initialMap
        for (branch in Branch.all)
            with(House(branch, progressTo ?: ming)) {
                if (!initialMap.containsKey(this))
                    newMap = newMap.plus(this to emptyList<Star>())
            }
        newMap
    }

    class ElementScores {
        class ElementScore(val element: Element) {
            var score: Int = 0
        }

        val scores: List<ElementScore> = Element.values().map { ElementScore(it) }
        fun score(element: Element) {
            scores[element.ordinal].score++
        }
    }
}

