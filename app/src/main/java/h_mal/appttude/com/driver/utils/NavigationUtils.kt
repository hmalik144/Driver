package h_mal.appttude.com.driver.utils

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.Navigation

const val UPLOAD_NEW = "upload_new"

fun navigateToActivity(context: Context, navigationActivity: Navigations) {
    try {
        val ourClass: Class<*> =
            Class.forName("h_mal.appttude.com.driver." + navigationActivity.value)
        val intent = Intent(context, ourClass)
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun View.navigateTo(@IdRes navId: Int) {
    Navigation
        .findNavController(this)
        .navigate(navId)
}


