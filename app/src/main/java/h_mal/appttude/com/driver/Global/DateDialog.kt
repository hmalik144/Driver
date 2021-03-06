package h_mal.appttude.com.driver.Global


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.widget.EditText
import androidx.annotation.RequiresApi
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.utils.DateUtils


private const val DATE_FORMAT = "dd/MM/yyyy"
@RequiresApi(api = Build.VERSION_CODES.N)
class DateDialog(
    private val editText: EditText
) : DatePickerDialog(editText.context, AlertDialog.THEME_HOLO_LIGHT) {

    var dateSetListener: OnDateSetListener =
        OnDateSetListener { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(year, month + 1, dayOfMonth)

            editText.setText(DateUtils.parseCalenderIntoDateString(cal, DATE_FORMAT))
        }

    init {
        val dateString = editText.text?.toString()
        val date = if (dateString.isNullOrBlank()){
            // Set time to now
            Calendar.getInstance()
        }else{
            // Parse current edit text string and set value
            DateUtils.parseDateStringIntoCalender(dateString, DATE_FORMAT)
                ?: Calendar.getInstance()
        }
        setDateFromCalender(date)
        setOnDateSetListener(dateSetListener)
        setTitle(context.getString(R.string.set_date))
        show()
    }

    private fun setDateFromCalender(calendar: Calendar){
        val mYear = calendar.get(Calendar.YEAR)
        val mMonth = calendar.get(Calendar.MONTH)
        val mDay = calendar.get(Calendar.DAY_OF_MONTH)

        updateDate(mYear, mMonth, mDay)
    }


}