package h_mal.appttude.com.driver.dialogs


import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.widget.EditText
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.utils.DateUtils


@Suppress("DEPRECATION")
class DateDialog(
    private val editText: EditText,
    dateSelected: (String?) -> Unit
) : DatePickerDialog(editText.context) {

    private val dateSetListener: OnDateSetListener =
        OnDateSetListener { _, year, month, dayOfMonth ->
            val date = DateUtils.getDateString(year, month, dayOfMonth)
            dateSelected(date)
            editText.setText(date)
            editText.error = null
        }

    init {
        datePicker.apply {
            spinnersShown = true
            calendarViewShown = false
        }
        val dateString = editText.text.toString()
        val date = DateUtils.parseDateStringIntoCalender(dateString)

        setDateFromCalender(date)
        setOnDateSetListener(dateSetListener)
        setTitle(context.getString(R.string.set_date))
        show()
    }

    private fun setDateFromCalender(calendar: org.joda.time.LocalDate) {
        updateDate(calendar.year, calendar.monthOfYear, calendar.dayOfMonth)
    }

}