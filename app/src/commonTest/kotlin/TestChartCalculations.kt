import com.meowbox.fourpillars.*
import kotlin.test.Test
import kotlin.test.assertEquals

class TestChartPlacement {
    val chartParameters = Chart(
        Pillar(Stem.YangFire, Branch.Dragon), Pillar(Stem.YangWater, Branch.Dragon),
        Pillar(Stem.YangMetal, Branch.Tiger), Pillar(Stem.YangMetal, Branch.Dragon), 9
    )

    val elementScores = chartParameters.elementScores

    @Test
    fun testInnerElement() {
        assertEquals(Element.Earth, chartParameters.innerElement)
    }

    @Test
    fun testMingLocation() {
        assertEquals(Branch.Rat, chartParameters.ming)
    }

    @Test
    fun ziWei_ShouldBeInTiger() {
        assertEquals(Branch.Tiger, Star.ZiWei.findIn(chartParameters))
    }

    @Test
    fun tianFu_ShouldBeInTiger() {
        assertEquals(Branch.Tiger, Star.TianFu.findIn(chartParameters))
    }

    @Test
    fun wenChang_Horse() {
        assertEquals(Branch.Horse, Star.WenChang.findIn(chartParameters))
    }

    @Test
    fun wenQu_Monkey() {
        assertEquals(Branch.Monkey, Star.WenQu.findIn(chartParameters))
    }

    @Test
    fun huoXing_Horse() {
        assertEquals(Branch.Horse, Star.HuoXing.findIn(chartParameters))
    }

    @Test
    fun lingXing_Tiger() {
        assertEquals(Branch.Tiger, Star.LingXing.findIn(chartParameters))
    }

    @Test
    fun yangRen_Horse() {
        assertEquals(Branch.Horse, Star.YangRen.findIn(chartParameters))
    }

    @Test
    fun tuoLuo_Dragon() {
        assertEquals(Branch.Dragon, Star.TuoLuo.findIn(chartParameters))
    }

    @Test
    fun youBi_Monkey() {
        assertEquals(Branch.Monkey, Star.YouBi.findIn(chartParameters))
    }

    @Test
    fun zuoFu_Horse() {
        assertEquals(Branch.Horse, Star.ZuoFu.findIn(chartParameters))
    }

    @Test
    fun tianCun_Storehouse_Snake() {
        assertEquals(Branch.Snake, Star.TianCun.findIn(chartParameters))
    }

    @Test
    fun tianYaoBeauty_Rabbit() {
        assertEquals(Branch.Rabbit, Star.TianYao.findIn(chartParameters))
    }

    @Test
    fun tianKui_Leader_Pig() {
        assertEquals(Branch.Pig, Star.TianKui.findIn(chartParameters))
    }

    @Test
    fun tianXi_Happiness_Snake() {
        assertEquals(Branch.Pig, Star.TianKui.findIn(chartParameters))
    }

    @Test
    fun diGong_Void_Goat() {
        assertEquals(Branch.Goat, Star.DiGong.findIn(chartParameters))
    }

    @Test
    fun tianYue_Halberd_Rooster() {
        assertEquals(Branch.Rooster, Star.TianYue.findIn(chartParameters))
    }

    @Test
    fun tianXing_Punishment_Pig() {
        assertEquals(Branch.Pig, Star.TianXing.findIn(chartParameters))
    }

    @Test
    fun diJie_Loss_Rabbit() {
        assertEquals(Branch.Rabbit, Star.DiJie.findIn(chartParameters))
    }

    @Test
    fun northernStars_ShouldBeInTheRightPlaces() {
        val stars = arrayOf(Star.TianJi, Star.TaiYang, Star.WuQu, Star.TianTong, Star.LianZhen)
        val positions = arrayOf(Branch.Ox, Branch.Pig, Branch.Dog, Branch.Rooster, Branch.Horse)
        for (i in 0..4)
            assertEquals(positions[i], stars[i].findIn(chartParameters))
    }

    @Test
    fun southernStars_ShouldBeInTheRightPlaces() {
        val stars =
            arrayOf(Star.TianXiang, Star.TaiYin, Star.JuMen, Star.TianLiang, Star.TanLang, Star.QiSha, Star.PoJun)
        val positions =
            arrayOf(Branch.Horse, Branch.Rabbit, Branch.Snake, Branch.Goat, Branch.Dragon, Branch.Monkey, Branch.Rat)
        for (i in 0..(stars.size - 1))
            assertEquals(positions[i], stars[i].findIn(chartParameters))
    }

    @Test
    fun elementScore_Wood_2() {
        assertEquals(2, elementScores.scores[0].score)
    }

    @Test
    fun elementScore_Fire_1() {
        assertEquals(1, elementScores.scores[1].score)
    }

    @Test
    fun elementScore_Earth_4() {
        assertEquals(4, elementScores.scores[2].score)
    }

    @Test
    fun elementScore_Metal_3() {
        assertEquals(3, elementScores.scores[3].score)
    }

    @Test
    fun elementScore_Water_2() {
        assertEquals(2, elementScores.scores[4].score)
    }

    private fun progressChart(to: Branch? = null) = Chart(
        chartParameters.yearPillar, chartParameters.monthPillar,
        chartParameters.dayPillar, chartParameters.hourPillar, chartParameters.dayOfMonth, to
    )

    @Test
    fun progressToDragon_TestPalaces() {
        val chart = progressChart(Branch.Dragon)
        assertEquals(Palace.Children, chart.houses.keys.toTypedArray().sortedArray()[1].palace)
    }

    @Test
    fun progressToHorse_TestPalaces() {
        val chart = progressChart(Branch.Horse)
        assertEquals(Palace.Property, chart.houses.keys.toTypedArray().sortedArray()[9].palace)
    }

    @Test
    fun inNatalPosition_TestPalaces() {
        val chart = progressChart(null)
        assertEquals(Palace.Property, chart.houses.keys.toTypedArray().sortedArray()[3].palace)
        assertEquals(Palace.Career, chart.houses.keys.toTypedArray().sortedArray()[6].palace)
    }

}
