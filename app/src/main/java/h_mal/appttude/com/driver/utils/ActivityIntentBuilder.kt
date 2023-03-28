package h_mal.appttude.com.driver.utils

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object ActivityIntentBuilder {

    inline fun <reified T : AppCompatActivity> Context.createIntent(): Intent =
        Intent(this, T::class.java)

    inline fun <reified T : AppCompatActivity> Fragment.createIntent() =
        requireContext().createIntent<T>()

}