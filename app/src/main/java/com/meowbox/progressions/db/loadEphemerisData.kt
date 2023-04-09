package com.meowbox.progressions.db

import android.content.Context
import android.util.Log
import com.meowbox.DateTime
import com.meowbox.fourpillars.Branch
import com.meowbox.fourpillars.Chart
import com.meowbox.fourpillars.Pillar
import com.meowbox.fourpillars.Stem
import com.meowbox.progressions.EphemerisPoint
import com.meowbox.progressions.R
import com.meowbox.progressions.monthPillar
import com.meowbox.progressions.yearPillar
import org.joda.time.Days
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import java.io.File
import java.util.zip.ZipInputStream

val EphemerisPoint.dayPillarNum: Int get() = (solarDate.id + 10).rem(60)
val EphemerisPoint.dayPillar: Pillar get() = Pillar.num(dayPillarNum)

fun EphemerisPoint.hourPillar(branch: Branch) =
    Pillar(Stem.all[(2 * dayPillar.ordinal + branch.ordinal) % 10], branch)

fun SolarDate(date: LocalDate) = SolarDate(Days.daysBetween(startDate, date).days)
fun SolarDate.toLocalDate() = startDate.plusDays(id)

private val startDate = LocalDate(1900, 1, 1)

fun DateTime.toLocalDateTime() = LocalDateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour)
fun DateTime.toSolarDate() = SolarDate(toLocalDateTime().toLocalDate())

actual fun loadChart(dob: DateTime): Chart = dob.toLocalDateTime().plusHours(1).let { localDateTime ->
    Db.instance.ephemerisPointQueries.getBySolarDate(SolarDate(localDateTime.toLocalDate())).executeAsOne()
        .let { ephemerisPoint: EphemerisPoint ->
            Chart(
                ephemerisPoint.yearPillar,
                ephemerisPoint.monthPillar,
                ephemerisPoint.dayPillar,
                ephemerisPoint.hourPillar(Branch.num(localDateTime.hourOfDay / 2)),
                ephemerisPoint.lunarDate.dayOfMonth
            )
        }
}


/************************************************************************
 * Called from MainActivity.
 */
fun loadDatabase(context: Context, dbName: String) =
    File(context.getDatabasePath(dbName).toURI()).createRecursive { file ->
        ZipInputStream(context.resources.openRawResource(R.raw.ephemerisdb)).also { zip ->
            Log.w("loadDatabase", "Creating new database.")
            zip.nextEntry
            var len: Int
            val buffer = ByteArray(10000)
            val out = file.outputStream()
            while (true) {
                len = zip.read(buffer)
                if (len == -1)
                    break
                out.write(buffer, 0, len)
            }
        }
    }

/************************************************************************
 * Used only by loadDatabase() for creating the empty database file
 *      during initialization.
 */
private inline fun File.createRecursive(noinline block: ((File) -> Unit)? = null) {
    if (!parentFile.exists())
        parentFile.mkdirs()

    if (createNewFile() && block != null)
        block(this)
}
