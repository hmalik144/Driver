package h_mal.appttude.com.driver.ui.permission

import android.content.Context
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog


class PermissionsDeclarationDialog(context: Context) : BaseDeclarationDialog(context) {

    override val link: String = "https://sites.google.com/view/hmaldev/home/choice-cars"
    override val message: String =
        "Storage is required to access images on the devices"
}

abstract class BaseDeclarationDialog(val context: Context) : DeclarationBuilder {
    abstract override val link: String
    abstract override val message: String

    lateinit var dialog: AlertDialog

    fun showDialog(agreeCallback: () -> Unit = { }, disagreeCallback: () -> Unit = { }) {
        val myMessage = buildMessage()

        val builder = AlertDialog.Builder(context)
            .setPositiveButton("agree") { _, _ ->
                agreeCallback()
            }
            .setNegativeButton("disagree") { _, _ ->
                disagreeCallback()
            }
            .setMessage(myMessage)
            .setCancelable(false)

        dialog = builder.create()
        dialog.show()

        // Make the textview clickable. Must be called after show()
        val msgTxt = dialog.findViewById<View>(android.R.id.message) as TextView?
        msgTxt?.movementMethod = LinkMovementMethod.getInstance()
    }

    fun dismiss() = dialog.dismiss()
}

