package h_mal.appttude.com.driver.dialogs

import android.app.Activity
import android.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlin.system.exitProcess

object ExitDialog{

    fun Activity.displayExitDialog() = AlertDialog.Builder(this)
            .setTitle("Leave?")
            .setMessage("Are you sure you want to exit?")
            .setNegativeButton(android.R.string.no, null)
            .setPositiveButton(
                android.R.string.yes
            ) { _, _ ->
                this.finish()
                exitProcess(0)
            }
            .create()
            .show()

    fun Fragment.displayExitDialog() = requireActivity().displayExitDialog()
}