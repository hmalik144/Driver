package h_mal.appttude.com.driver.utils

import android.icu.util.Calendar
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

    fun parseDateStringIntoCalender(dateString: String, format: String): Calendar? {
        val dateFormat = getSimpleDateFormat(format)
        val calendar = Calendar.getInstance()
        return try {
            calendar.time = dateFormat.parse(dateString)
            calendar
        } catch (e: Exception) {
            null
        }
    }

    fun parseCalenderIntoDateString(calendar: Calendar, format: String): String? {
        val date = calendar.time
        val dateFormat = getSimpleDateFormat(format)

        return try {
            dateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }
}
