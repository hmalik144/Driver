package h_mal.appttude.com.driver;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateDialog extends DatePickerDialog {

    int mYear;
    int mMonth;
    int mDay;

    EditText editText;

    public DateDialog(@NonNull Context context) {
        super(context);
    }

    public DateDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public DateDialog(@NonNull Context context,
                      @Nullable DatePickerDialog.OnDateSetListener listener,
                      int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);


    }

    public DateDialog(@NonNull Context context, int themeResId, @Nullable DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, themeResId, listener, year, monthOfYear, dayOfMonth);


    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    public void setOnDateSetListener(@Nullable DatePickerDialog.OnDateSetListener listener) {
        super.setOnDateSetListener(dateSetListener);
    }

    public void init(EditText editText){
        this.editText = editText;

        String dateString = editText.getText().toString();

        Date javaDate = null;

        if(TextUtils.isEmpty(dateString)){
            Calendar calendar = Calendar.getInstance();
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
        }else {
            try {
                SimpleDateFormat sdfrmt = new SimpleDateFormat("dd-MM-yyyy");
                sdfrmt.setLenient(false);
                javaDate = sdfrmt.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }finally {
                if (javaDate != null) {
                    mYear = Integer.parseInt(dateString.substring(6, 9));
                    mMonth = Integer.parseInt(dateString.substring(4, 5));
                    mDay = Integer.parseInt(dateString.substring(1, 2));
                }else {
                    Calendar calendar = Calendar.getInstance();
                    mYear = calendar.get(Calendar.YEAR);
                    mMonth = calendar.get(Calendar.MONTH);
                    mDay = calendar.get(Calendar.DAY_OF_MONTH);
                }
            }

        }

        Log.i(this.getClass().getSimpleName(), "init: year =" + mYear +
                "month = " + mMonth +
                "day = " + mDay);


        updateDate(mYear,mMonth,mDay);

        setOnDateSetListener(null);

        this.setTitle(getContext().getString(R.string.set_date));
        this.show();
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month + 1;
            mDay = dayOfMonth;

            editText.setText(String.format("%02d", mDay) + "-" +
                    String.format("%02d", (mMonth)) +"-" +
                    mYear
            );

        }
    };

}
