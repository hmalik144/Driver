package h_mal.appttude.com.driver.utils

import h_mal.appttude.com.driver.utils.DateUtils.convertDateStringDatePattern
import org.junit.Assert.*
import org.junit.Test

class DateUtilsTest {

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
    fun test_parseCalenderIntoDateString() {
    }
}