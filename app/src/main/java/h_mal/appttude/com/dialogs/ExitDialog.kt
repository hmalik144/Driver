package h_mal.appttude.com.dialogs

import android.app.Activity
import android.app.AlertDialog
import h_mal.appttude.com.R
import kotlin.system.exitProcess

object ExitDialog {

    fun Activity.displayExitDialog() = AlertDialog.Builder(this)
        .setTitle(getString(R.string.leave_header))
        .setMessage(getString(R.string.leave_message))
        .setNegativeButton(android.R.string.cancel, null)
        .setPositiveButton(
            android.R.string.ok
        ) { _, _ ->
            finish()
            exitProcess(0)
        }
        .create()
        .show()

}