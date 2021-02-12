package h_mal.appttude.com.driver.Global

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.widget.DatePicker
import android.widget.EditText
import androidx.annotation.RequiresApi
import h_mal.appttude.com.driver.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DateDialog : DatePickerDialog {
    var mYear: Int = 0
    var mMonth: Int = 0
    var mDay: Int = 0
    var editText: EditText? = null

    @RequiresApi(api = Build.VERSION_CODES.N)
    constructor(context: Context) : super(context)

    @RequiresApi(api = Build.VERSION_CODES.N)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    constructor(
        context: Context,
        listener: OnDateSetListener?,
        year: Int, month: Int, dayOfMonth: Int
    ) : super(context, listener, year, month, dayOfMonth)

    constructor(
        context: Context,
        themeResId: Int,
        listener: OnDateSetListener?,
        year: Int,
        monthOfYear: Int,
        dayOfMonth: Int
    ) : super(context, themeResId, listener, year, monthOfYear, dayOfMonth)

    override fun setTitle(title: CharSequence) {
        super.setTitle(title)
    }

    override fun setOnDateSetListener(listener: OnDateSetListener?) {
        super.setOnDateSetListener(dateSetListener)
    }

    fun init(editText: EditText?) {
        this.editText = editText
        val dateString: String = editText!!.text.toString()
        var javaDate: Date? = null
        if (TextUtils.isEmpty(dateString)) {
            val calendar: Calendar = Calendar.getInstance()
            mYear = calendar.get(Calendar.YEAR)
            mMonth = calendar.get(Calendar.MONTH)
            mDay = calendar.get(Calendar.DAY_OF_MONTH)
        } else {
            try {
                val sdfrmt: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                sdfrmt.isLenient = false
                javaDate = sdfrmt.parse(dateString)
            } catch (e: ParseException) {
                e.printStackTrace()
            } finally {
                if (javaDate != null) {
                    mYear = dateString.substring(6, dateString.length).toInt()
                    mMonth = dateString.substring(3, 5).toInt() - 1
                    mDay = dateString.substring(0, 2).toInt()
                } else {
                    val calendar: Calendar = Calendar.getInstance()
                    mYear = calendar.get(Calendar.YEAR)
                    mMonth = calendar.get(Calendar.MONTH)
                    mDay = calendar.get(Calendar.DAY_OF_MONTH)
                }
            }
        }
        Log.i(
            this.javaClass.simpleName, ("init: year =" + mYear +
                    "month = " + mMonth +
                    "day = " + mDay)
        )
        updateDate(mYear, mMonth, mDay)
        setOnDateSetListener(null)
        this.setTitle(context.getString(R.string.set_date))
        show()
    }

    var dateSetListener: OnDateSetListener = object : OnDateSetListener {
        override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
            mYear = year
            mMonth = month + 1
            mDay = dayOfMonth
            editText!!.setText(
                (String.format("%02d", mDay) + "/" + String.format("%02d", (mMonth)) + "/" +
                        mYear)
            )
        }
    }
}