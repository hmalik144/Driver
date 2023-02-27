package h_mal.appttude.com.ui

import android.app.AlertDialog
import android.content.Context
import android.view.View
import androidx.cardview.widget.CardView


class DriverStatusClass : View.OnClickListener {
    var userId: String? = null
    var cardView: CardView? = null
    var context: Context? = null
    var currentSelection: Boolean = false
    override fun onClick(v: View) {
        val choices: Array<String> = arrayOf("Active", "Inactive")
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context)
        var selection: Int = -1
        if (currentSelection) {
            selection = 0
        } else if (!currentSelection) {
            selection = 1
        }
        alertDialog.setSingleChoiceItems(
            choices,
            selection
        ) { dialog, which -> }
        alertDialog.create().show()
    }

    private fun SetStatus(status: Boolean) {
//        MainActivity.mDatabase!!.child(FirebaseClass.USER_FIREBASE)
//            .child((userId)!!).child(FirebaseClass.DRIVER_STATUS).setValue(status)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    cardView!!.setBackgroundColor(setStatusColour(status))
//                } else {
//                }
//            }
    }

    private fun setStatusColour(b: Boolean): Int {
        if (b) {
            return android.R.color.holo_green_dark
        } else {
            return android.R.color.holo_red_dark
        }
    } //    public int getOverApprovalStatusCode(WholeDriverObject wholeDriverObject){
    //
    //        if (wholeDriverObject.approvalsObject != null){
    //            ApprovalsObject approvalsObject = wholeDriverObject.approvalsObject;
    //
    //            int[] ints = new int[]{approvalsObject.getDriver_details_approval(),
    //                    approvalsObject.driver_license_approval,
    //                    approvalsObject.private_hire_approval,
    //                    approvalsObject.vehicle_details_approval,
    //                    approvalsObject.insurance_details_approval,
    //                    approvalsObject.mot_details_approval,
    //                    approvalsObject.log_book_approval,
    //                    approvalsObject.ph_car_approval};
    //
    //
    //            return setImageResource(mode(ints));
    //        }
    //
    //        return setImageResource(NO_DATE_PRESENT);
    //    }
    //
    //    public void setStatusCode(String userId,String approvalNameString,int status){
    //
    //        if (!approvalNameString.equals("")) {
    //            mDatabase.child(USER_FIREBASE).child(userId).child(USER_APPROVALS).child(approvalNameString)
    //                    .setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
    //                @Override
    //                public void onComplete(@NonNull Task<Void> task) {
    //                    if (task.isSuccessful()) {
    //
    //                    } else {
    //
    //                    }
    //                }
    //            });
    //        }
    //
    //
    //    }
    //
    //    public int setImageResource(int statusCode){
    //        int imageResource;
    //
    //        switch (statusCode){
    //            case APPROVAL_PENDING:
    //                imageResource = R.drawable.pending;
    //                break;
    //            case APPROVAL_DENIED:
    //                imageResource = R.drawable.denied;
    //                break;
    //            case APPROVED:
    //                imageResource = R.drawable.approved;
    //                break;
    //            default:
    //                imageResource = R.drawable.zero;
    //                break;
    //        }
    //
    //        return imageResource;
    //    }
}