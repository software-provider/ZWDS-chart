package com.meowbox.progressions.db

import android.content.Context
import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
import com.meowbox.DateTime
import com.meowbox.fourpillars.Branch
import com.meowbox.fourpillars.Pillar
import com.meowbox.fourpillars.Stem
import com.meowbox.progressions.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals


@RunWith(RobolectricTestRunner::class)
@Config(minSdk = Build.VERSION_CODES.LOLLIPOP_MR1, maxSdk = Build.VERSION_CODES.LOLLIPOP_MR1)
class LunarDateTest {
    companion object {
        val dbName = "test.sqlite3"
    }

    var context: Context? = null

    @Test
    fun there_ShouldBe_73018_EphemerisPoints_InDatabase() {
        assertEquals(73018, Db.instance.ephemerisPointQueries.count().executeAsOne().toInt())
    }

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().context
        loadDatabase(context!!, dbName)
        Db.dbSetup(AndroidSqliteDriver(Database.Schema, context!!, dbName))
    }

    @After
    fun teardown() {
        Db.dbClear()
    }

    @Test
    fun testTpgBirthday() {
        val birthday = DateTime(1978, 4, 7, 15, 30)
        val ephemerisPoint = Db.instance.ephemerisPointQueries.getBySolarDate(birthday.toSolarDate()).executeAsOne()
        assertEquals(Pillar(Stem.YangEarth, Branch.Horse), ephemerisPoint.lunarDate.yearPillar)
        assertEquals(Pillar(Stem.YangFire, Branch.Dragon), ephemerisPoint.lunarDate.monthPillar)
        assertEquals(Pillar(Stem.YinEarth, Branch.Pig), ephemerisPoint.dayPillar)
        assertEquals(Pillar(Stem.YangWater, Branch.Monkey), ephemerisPoint.hourPillar(Branch.Monkey))
    }

    @Test
    fun verifyPillarsFor_Jun_18_1982() {
        val vanessaBirthday = loadChart(DateTime(1982, 6, 18, 15, 0))
        assertEquals(27, vanessaBirthday.dayOfMonth)
        assertEquals(Pillar(Stem.YangWater, Branch.Dog), vanessaBirthday.yearPillar)
        assertEquals(Pillar(Stem.YinWood, Branch.Snake), vanessaBirthday.monthPillar)
        assertEquals(Pillar(Stem.YangWater, Branch.Monkey), vanessaBirthday.dayPillar)
        assertEquals(Pillar(Stem.YangEarth, Branch.Monkey), vanessaBirthday.hourPillar)

    }


    @Test
    fun testTpgChartComments() {
        val birthday = DateTime(1978, 4, 7, 15, 30)
        val chart = loadChart(birthday)
        assertEquals(Pillar(Stem.YangEarth, Branch.Horse), chart.yearPillar)
        assertEquals(Pillar(Stem.YangFire, Branch.Dragon), chart.monthPillar)
        assertEquals(Pillar(Stem.YinEarth, Branch.Pig), chart.dayPillar)
        assertEquals(Pillar(Stem.YangWater, Branch.Monkey), chart.hourPillar)
    }
}
