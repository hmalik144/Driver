package h_mal.appttude.com.dialogs

import android.app.Activity
import android.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlin.system.exitProcess

object ExitDialog{

    fun Activity.displayExitDialog() = AlertDialog.Builder(this)
            .setTitle("Leave?")
            .setMessage("Are you sure you want to exit?")
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ ->
                finish()
                exitProcess(0)
            }
            .create()
            .show()

    fun Fragment.displayExitDialog() = requireActivity().displayExitDialog()
}