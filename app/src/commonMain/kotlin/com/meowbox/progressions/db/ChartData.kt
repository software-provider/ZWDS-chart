package com.meowbox.progressions.db

import com.meowbox.DateTime
import com.meowbox.fourpillars.Chart
import com.meowbox.fourpillars.Palace
import com.meowbox.fourpillars.Star
import com.meowbox.progressions.StarComment
import com.meowbox.progressions.getValue

object ChartData {
    private fun Chart.findStarPalace(star: Star) = houses.filter { it.value.contains(star) }.keys.first().palace

    fun loadMyChartRecords() = Db.instance.chartRecordQueries.mine().executeAsList()
    fun searchChartRecords(search: String) = Db.instance.chartRecordQueries.search("%$search%").executeAsList()
//    fun createChartRecord(name: String, dob: DateTime, yearPillar: Pillar, monthPillar: Pillar, dayPillar: Pillar, hourPillar: Pillar) =
//            Db.instance.chartRecordQueries.insertOne(SolarDate())

    fun getEphemerisPoint(solarDate: SolarDate) =
        Db.instance.ephemerisPointQueries.getBySolarDate(solarDate).executeAsOne()

    fun getCommentsForPalace(chart: Chart, palace: Palace): Map<Star, List<StarComment>> =
        chart.houses.getValue(palace).let { stars ->
            ChartData.getCommentsForPalace(palace, stars)
        }

    fun getCommentsForPalace(palace: Palace, stars: List<Star>): Map<Star, List<StarComment>> =
        stars.map { star ->
            star to Db.instance.starCommentQueries.getByPalace(star, palace).executeAsList().filter { starComment ->
                starComment.inHouseWith.all { it.intersect(stars).isNotEmpty() }
            }
        }.toMap()

    fun saveChart(solarDate: SolarDate, dob: DateTime, name: String) = with(loadChart(dob)) {
        Db.instance.chartRecordQueries.insertOne(
            solarDate,
            name,
            dob,
            yearPillar.branch,
            yearPillar.stem,
            hourPillar.branch,
            hourPillar.stem,
            ming,
            findStarPalace(Star.ZiWei),
            findStarPalace(Star.TianFu)
        )
    }
}
