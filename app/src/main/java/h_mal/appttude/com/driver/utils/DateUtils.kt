package h_mal.appttude.com.driver.utils

import h_mal.appttude.com.driver.application.GLOBAL_FORMAT
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object DateUtils {

    fun getDateTimeStamp(): String {
        val sdf: SimpleDateFormat = getSimpleDateFormat("yyyyMMdd_HHmmss")
        return sdf.format(Date())
    }

    @Throws(ParseException::class)
    fun String.convertDateStringDatePattern(formatIn: String, formatOut: String): String {
        return try {
            val sdfIn = getSimpleDateFormat(formatIn)
            val sdfOut = getSimpleDateFormat(formatOut)
            val newDate = sdfIn.parse(this)
            sdfOut.format(newDate)
        } catch (e: Exception) {
            e.printStackTrace()
            this
        }
    }

    private fun getSimpleDateFormat(format: String) = SimpleDateFormat(format, Locale.getDefault())

    fun parseDateStringIntoCalender(dateString: String, format: String = GLOBAL_FORMAT): LocalDate {
        if (dateString.isBlank()) {
            return LocalDate.now()
        }
        val dtf = DateTimeFormat.forPattern(format)
        return dtf.parseLocalDate(dateString)
    }

    fun getDateString(year: Int, month: Int, dayOfMonth: Int): String {
        val date = LocalDate.now()
            .withYear(year)
            .withMonthOfYear(month + 1)
            .withDayOfMonth(dayOfMonth)
        return date.toString(GLOBAL_FORMAT)
    }
}
