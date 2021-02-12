package h_mal.appttude.com.driver.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun Activity.displayToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.displayToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}