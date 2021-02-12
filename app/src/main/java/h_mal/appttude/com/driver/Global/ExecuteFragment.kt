package h_mal.appttude.com.driver.Global

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import h_mal.appttude.com.driver.MainActivity
import h_mal.appttude.com.driver.R

object ExecuteFragment {
    val UPLOAD_NEW: String = "upload_new"
    fun executeFragment(fragment: Fragment, bundle: Bundle?) {
        executeFragmentMethod(fragment, bundle)
    }

    fun executeFragment(fragment: Fragment?) {
        executeFragmentMethod(fragment)
    }

    fun executeFragment(fragment: Fragment?, userId: String?) {
        executeFragmentMethod(fragment, userId)
    }

    fun executeFragment(fragment: Fragment, userId: String?, archive: String?) {
        executeFragmentMethod(fragment, userId, archive)
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
}