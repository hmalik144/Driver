package h_mal.appttude.com.driver.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import h_mal.appttude.com.driver.MainActivity
import h_mal.appttude.com.driver.R

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

fun executeFragment(fragment: Fragment, bundle: Bundle?) {
    executeFragmentMethod(fragment, bundle)
}

fun executeFragment(fragment: Fragment) {
    executeFragmentMethod(fragment)
}

fun executeFragment(fragment: Fragment, userId: String?) {
    executeFragmentMethod(fragment, userId)
}

fun executeFragment(fragment: Fragment, userId: String?, archive: String?) {
        `executeFragmentMethod`(fragment, userId, archive)
}

private fun executeFragmentMethod(f: Fragment?) {
    val fragmentTransaction: FragmentTransaction =
        MainActivity.fragmentManager!!.beginTransaction()
    fragmentTransaction.replace(R.id.container, (f)!!)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .addToBackStack(f.javaClass.simpleName).commit()
}

private fun executeFragmentMethod(f: Fragment?, user_id: String?) {
    val bundle: Bundle = Bundle()
    bundle.putString("user_id", user_id)
    f!!.arguments = bundle
    val fragmentTransaction: FragmentTransaction =
        MainActivity.fragmentManager!!.beginTransaction()
    fragmentTransaction.replace(R.id.container, (f))
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .addToBackStack(f.javaClass.simpleName).commit()
}

private fun executeFragmentMethod(f: Fragment, user_id: String?, archive: String?) {
    val bundle: Bundle = Bundle()
    bundle.putString("user_id", user_id)
    bundle.putString("archive", archive)
    f.arguments = bundle
    val fragmentTransaction: FragmentTransaction =
        MainActivity.fragmentManager!!.beginTransaction()
    fragmentTransaction.replace(R.id.container, f)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .addToBackStack(f.javaClass.simpleName).commit()
}

private fun executeFragmentMethod(f: Fragment, b: Bundle?) {
    if (b != null) {
        f.arguments = b
    }
    val fragmentTransaction: FragmentTransaction =
        MainActivity.fragmentManager!!.beginTransaction()
    fragmentTransaction.replace(R.id.container, f)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .addToBackStack(f.javaClass.simpleName).commit()
}


