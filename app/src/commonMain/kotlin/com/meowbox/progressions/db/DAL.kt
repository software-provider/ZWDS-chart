package com.meowbox.progressions.db

import com.meowbox.DateTime
import com.meowbox.fourpillars.Branch
import com.meowbox.fourpillars.Chart
import com.meowbox.fourpillars.Star
import com.meowbox.progressions.ChartRecord
import com.meowbox.progressions.Database
import com.meowbox.progressions.EphemerisPoint
import com.meowbox.progressions.StarComment
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.EnumColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.serialization.internal.EnumSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import kotlinx.serialization.set

private val branchListSerializer = EnumSerializer(Branch::class).list
private val starSetListSerializer = EnumSerializer(Star::class).set.list
expect fun loadChart(dob: DateTime): Chart

fun createQueryWrapper(driver: SqlDriver) = Database(
    driver = driver,
    starCommentAdapter = StarComment.Adapter(
        starAdapter = EnumColumnAdapter(),
        palaceAdapter = EnumColumnAdapter(),
        auspicesAdapter = EnumColumnAdapter(),
        branchAdapter = object : ColumnAdapter<List<Branch>, String> {
            override fun encode(value: List<Branch>) = Json.stringify(branchListSerializer, value)
            override fun decode(databaseValue: String) =
                Json.parse(branchListSerializer, databaseValue)
        },
        inHouseWithAdapter = object : ColumnAdapter<List<Set<Star>>, String> {
            override fun encode(value: List<Set<Star>>) = Json.stringify(starSetListSerializer, value)
            override fun decode(databaseValue: String) =
                Json.parse(starSetListSerializer, databaseValue)
        }
    ),
    ephemerisPointAdapter = EphemerisPoint.Adapter(
        solarDateAdapter = object : ColumnAdapter<SolarDate, Long> {
            override fun encode(value: SolarDate): Long = value.id.toLong()
            override fun decode(databaseValue: Long) = SolarDate(databaseValue.toInt())
        },
        lunarDateAdapter = object : ColumnAdapter<LunarDate, Long> {
            override fun encode(value: LunarDate): Long = value.id.toLong()
            override fun decode(databaseValue: Long) = LunarDate(databaseValue.toShort())
        }
    ),
    chartRecordAdapter = ChartRecord.Adapter(
        idAdapter = object : ColumnAdapter<ChartRecordId, Long> {
            override fun decode(databaseValue: Long) = ChartRecordId(databaseValue.toInt())
            override fun encode(value: ChartRecordId) = value.id.toLong()
        },
        ephemerisPointIdAdapter = object : ColumnAdapter<SolarDate, Long> {
            override fun encode(value: SolarDate): Long = value.id.toLong()
            override fun decode(databaseValue: Long) = SolarDate(databaseValue.toInt())
        },
        dobAdapter = object : ColumnAdapter<DateTime, Long> {
            override fun decode(databaseValue: Long) = DateTime(databaseValue.toInt())
            override fun encode(value: DateTime) = value.id.toLong()
        },
        yearBranchAdapter = EnumColumnAdapter(),
        yearStemAdapter = EnumColumnAdapter(),
        hourBranchAdapter = EnumColumnAdapter(),
        hourStemAdapter = EnumColumnAdapter(),
        mingAdapter = EnumColumnAdapter(),
        ziWeiAdapter = EnumColumnAdapter(),
        tianFuAdapter = EnumColumnAdapter(),
        roddenRatingAdapter = EnumColumnAdapter()
    )
)
