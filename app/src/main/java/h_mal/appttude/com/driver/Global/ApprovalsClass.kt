package h_mal.appttude.com.driver.Global

import h_mal.appttude.com.driver.Objects.ApprovalsObject
import h_mal.appttude.com.driver.Objects.WholeDriverObject
import h_mal.appttude.com.driver.R


class ApprovalsClass {
    fun getOverApprovalStatusCode(wholeDriverObject: WholeDriverObject?): Int {
        if (wholeDriverObject!!.approvalsObject != null) {
            val approvalsObject: ApprovalsObject = wholeDriverObject!!.approvalsObject!!
            val ints: IntArray = intArrayOf(
                approvalsObject.driver_details_approval,
                approvalsObject.driver_license_approval,
                approvalsObject.private_hire_approval,
                approvalsObject.vehicle_details_approval,
                approvalsObject.insurance_details_approval,
                approvalsObject.mot_details_approval,
                approvalsObject.log_book_approval,
                approvalsObject.ph_car_approval
            )
            return setImageResource(mode(ints))
        }
        return setImageResource(FirebaseClass.NO_DATE_PRESENT)
    }

    fun setStatusCode(userId: String?, approvalNameString: String, status: Int) {
//        if (!(approvalNameString == "")) {
//            MainActivity.mDatabase!!.child(FirebaseClass.USER_FIREBASE)
//                .child((userId)!!).child(FirebaseClass.USER_APPROVALS)
//                .child(approvalNameString)
//                .setValue(status).addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                    } else {
//                    }
//                }
//        }
    }

    fun setImageResource(statusCode: Int): Int {
        val imageResource: Int
        when (statusCode) {
            FirebaseClass.APPROVAL_PENDING -> imageResource = R.drawable.pending
            FirebaseClass.APPROVAL_DENIED -> imageResource = R.drawable.denied
            FirebaseClass.APPROVED -> imageResource = R.drawable.approved
            else -> imageResource = R.drawable.zero
        }
        return imageResource
    }

    companion object {
        fun mode(array: IntArray): Int {
            var mode: Int = array.get(0)
            var maxCount: Int = 0
            if (matchedArray(array, 3)) {
                return 3
            } else if (matchedArray(array, 0)) {
                return 0
            } else {
                for (i in array.indices) {
                    val value: Int = array.get(i)
                    var count: Int = 1
                    for (j in array.indices) {
                        if (array.get(j) == value) count++
                        if (count > maxCount) {
                            mode = value
                            maxCount = count
                        }
                    }
                }
                if (mode == 3) {
                    return 1
                }
            }
            return mode
        }

        private fun matchedArray(array: IntArray, match: Int): Boolean {
            for (i: Int in array) {
                if (i != match) {
                    return false
                }
            }
            return true
        }
    }
}