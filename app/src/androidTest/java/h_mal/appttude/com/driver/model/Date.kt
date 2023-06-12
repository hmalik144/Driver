package h_mal.appttude.com.driver.model

import java.time.LocalDate

data class Date(
    val dayOfMonth: Int,
    val monthOfYear: Int,
    val year: Int
) {

    companion object {
        @JvmStatic
        fun now(): Date {
            val date = LocalDate.now()
            return Date(date.dayOfMonth, date.month.value, date.year)
        }
    }

}