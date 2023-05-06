package h_mal.appttude.com.driver.utils

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.Navigation

fun View.navigateTo(@IdRes navId: Int, args: Bundle? = null) {
    Navigation
        .findNavController(this)
        .navigate(navId, args)
}

fun Any.toBundle(key: String): Bundle {
    return Bundle().apply {
        when (this@toBundle) {
            is String -> putString(key, this@toBundle)
            is Int -> putInt(key, this@toBundle)
            is Boolean -> putBoolean(key, this@toBundle)
            is Parcelable -> putParcelable(key, this@toBundle)
            is Double -> putDouble(key, this@toBundle)
            is Float -> putFloat(key, this@toBundle)
        }

    }
}


