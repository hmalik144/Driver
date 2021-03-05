package h_mal.appttude.com.driver.Global

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.ImageView


class SetApprovalDialog constructor(
    var statusCode: Int,
    private val activity: Activity,
    private val userId: String?,
    position: Int,
    private val imageView: ImageView
) {
    private val groupNames: Array<String> = arrayOf("Pending", "Denied", "Approved")
    private val approvalNameString: String
    fun init() {
        val checkedItem: Int
        when (statusCode) {
            FirebaseClass.APPROVAL_PENDING -> checkedItem = 0
            FirebaseClass.APPROVAL_DENIED -> checkedItem = 1
            FirebaseClass.APPROVED -> checkedItem = 2
            else -> checkedItem = -1
        }
        val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(
            activity
        )
        alertBuilder.setSingleChoiceItems(groupNames, checkedItem, singleChoiceListener)
        //                .setPositiveButton(android.R.string.ok, submit);
        alertBuilder.create().ownerActivity
        alertBuilder.show()
    }

    var singleChoiceListener: DialogInterface.OnClickListener =
        object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                when (which) {
                    0 -> {
                        statusCode = FirebaseClass.APPROVAL_PENDING
                        publishStatuscode(statusCode, dialog)
                        return
                    }
                    1 -> {
                        statusCode = FirebaseClass.APPROVAL_DENIED
                        publishStatuscode(statusCode, dialog)
                        return
                    }
                    2 -> {
                        statusCode = FirebaseClass.APPROVED
                        publishStatuscode(statusCode, dialog)
                        return
                    }
                }
            }
        }

    private fun publishStatuscode(status: Int, dialog: DialogInterface) {

//        if (!(approvalNameString == "")) {
//            MainActivity.mDatabase!!.child(FirebaseClass.USER_FIREBASE).child(
//                (userId)!!
//            ).child(FirebaseClass.USER_APPROVALS).child(approvalNameString)
//                .setValue(status).addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Toast.makeText(activity, "Status change successful", Toast.LENGTH_SHORT)
//                            .show()
//                        imageView.setImageResource(
//                            MainActivity.approvalsClass!!.setImageResource(
//                                status
//                            )
//                        )
//                        dialog.dismiss()
//                    } else {
//                        Toast.makeText(
//                            activity,
//                            "Status change unsuccessful",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                }
//        } else {
//            Toast.makeText(activity, "Could not push status", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun getElement(i: Int): String {
        var element: String = ""
        when (i) {
            0 -> element =
                FirebaseClass.DRIVER_DETAILS_FIREBASE + FirebaseClass.APPROVAL_CONSTANT
            1 -> element =
                FirebaseClass.DRIVERS_LICENSE_FIREBASE + FirebaseClass.APPROVAL_CONSTANT
            2 -> element =
                FirebaseClass.PRIVATE_HIRE_FIREBASE + FirebaseClass.APPROVAL_CONSTANT
            3 -> element =
                FirebaseClass.VEHICLE_DETAILS_FIREBASE + FirebaseClass.APPROVAL_CONSTANT
            4 -> element =
                FirebaseClass.INSURANCE_FIREBASE + FirebaseClass.APPROVAL_CONSTANT
            5 -> element =
                FirebaseClass.MOT_FIREBASE + FirebaseClass.APPROVAL_CONSTANT
            6 -> element =
                FirebaseClass.LOG_BOOK_FIREBASE + FirebaseClass.APPROVAL_CONSTANT
            7 -> element =
                FirebaseClass.PRIVATE_HIRE_VEHICLE_LICENSE + FirebaseClass.APPROVAL_CONSTANT
        }
        return element
    }

    init {
        approvalNameString = getElement(position)
        init()
    }
}