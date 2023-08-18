package h_mal.appttude.com.driver.untiTests

import androidx.startup.AppInitializer
import androidx.test.platform.app.InstrumentationRegistry
import h_mal.appttude.com.driver.utils.DateUtils
import h_mal.appttude.com.driver.utils.DateUtils.convertDateStringDatePattern
import net.danlew.android.joda.JodaTimeInitializer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DateUtilsTest {

    @Before
    fun setup() {
        AppInitializer.getInstance(InstrumentationRegistry.getInstrumentation().context.applicationContext)
            .initializeComponent(JodaTimeInitializer::class.java)
    }

    @Test
    fun test_getDateTimeStamp() {
        val regex1 = "[0-9]{8}_[0-9]{6}".toRegex()
        val date = DateUtils.getDateTimeStamp()
        assertTrue(regex1 matches date)
    }

    @Test
    fun test_convertDateStringDatePattern() {
        val inDate = "12/12/2020"
        val outDate1 = inDate.convertDateStringDatePattern("dd/MM/yyyy", "yyyyMMdd_HHmmss")
        val regex1 = "[0-9]{8}_[0-9]{6}".toRegex()
        assertTrue(regex1 matches outDate1)

        // failed match in
        val outDate2 = inDate.convertDateStringDatePattern("yyyy-MM-dd HH:mm:ss", "yyyyMMdd_HHmmss")
        assertFalse(regex1 matches outDate2)
        assertEquals(inDate, outDate2)
    }

    @Test
    fun test_parseDateStringIntoCalender() {
    }

    @Test
    fun test_getDateString() {
        val date = DateUtils.getDateString(2019, 8, 1)
        assertEquals(date, "01/08/2019")
    }
}