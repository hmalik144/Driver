package h_mal.appttude.com.driver.Global;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import h_mal.appttude.com.driver.R;

import static h_mal.appttude.com.driver.Global.ExecuteFragment.executeFragment;
import static h_mal.appttude.com.driver.Global.FirebaseClass.APPROVAL_CONSTANT;
import static h_mal.appttude.com.driver.Global.FirebaseClass.APPROVAL_DENIED;
import static h_mal.appttude.com.driver.Global.FirebaseClass.APPROVAL_PENDING;
import static h_mal.appttude.com.driver.Global.FirebaseClass.APPROVED;
import static h_mal.appttude.com.driver.Global.FirebaseClass.DRIVERS_LICENSE_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.DRIVER_DETAILS_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.INSURANCE_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.LOG_BOOK_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.MOT_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.PRIVATE_HIRE_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.PRIVATE_HIRE_VEHICLE_LICENSE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.USER_APPROVALS;
import static h_mal.appttude.com.driver.Global.FirebaseClass.USER_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.VEHICLE_DETAILS_FIREBASE;
import static h_mal.appttude.com.driver.MainActivity.approvalsClass;
import static h_mal.appttude.com.driver.MainActivity.mDatabase;
import static h_mal.appttude.com.driver.MainActivity.viewController;

public class SetApprovalDialog{

    private final String[] groupNames = {"Pending","Denied","Approved"};
    private String approvalNameString;

    public int statusCode;
    private Activity activity;
    private String userId;
    private ImageView imageView;

    public SetApprovalDialog(int statusCode, Activity activity, String userId, int position, ImageView imageView) {
        this.statusCode = statusCode;
        this.activity = activity;
        this.userId = userId;
        this.imageView = imageView;
        this.approvalNameString = getElement(position);

        init();
    }

    public void init(){
        int checkedItem;
        switch (statusCode){
            case APPROVAL_PENDING:
                checkedItem = 0;
                break;
            case APPROVAL_DENIED:
                checkedItem = 1;
                break;
            case APPROVED:
                checkedItem = 2;
                break;
            default:
                checkedItem = -1;
                break;
        }

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
        alertBuilder.setSingleChoiceItems(groupNames, checkedItem, singleChoiceListener);
//                .setPositiveButton(android.R.string.ok, submit);
        alertBuilder.create().getOwnerActivity();
        alertBuilder.show();
    }

    DialogInterface.OnClickListener singleChoiceListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    statusCode = APPROVAL_PENDING;
                    publishStatuscode(statusCode, dialog);
                    return;
                case 1:
                    statusCode = APPROVAL_DENIED;
                    publishStatuscode(statusCode, dialog);
                    return;
                case 2:
                    statusCode = APPROVED;
                    publishStatuscode(statusCode, dialog);
                    return;
            }
        }
    };

    private void publishStatuscode(final int status, final DialogInterface dialog){
        viewController.progress(View.VISIBLE);

        if (!approvalNameString.equals("")){
            mDatabase.child(USER_FIREBASE).child(userId).child(USER_APPROVALS).child(approvalNameString)
                    .setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(activity, "Status change successful", Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(approvalsClass.setImageResource(status));
                        dialog.dismiss();
                    }else{
                        Toast.makeText(activity, "Status change unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                    viewController.progress(View.GONE);
                }
            });
        }else {
            Toast.makeText(activity, "Could not push status", Toast.LENGTH_SHORT).show();
        }

    }

    private String getElement(int i){
        String element = "";

        switch (i){
            case 0:
                element = DRIVER_DETAILS_FIREBASE + APPROVAL_CONSTANT;
                break;
            case 1:
                element = DRIVERS_LICENSE_FIREBASE + APPROVAL_CONSTANT;
                break;
            case 2:
                element = PRIVATE_HIRE_FIREBASE + APPROVAL_CONSTANT;
                break;
            case 3:
                element = VEHICLE_DETAILS_FIREBASE + APPROVAL_CONSTANT;
                break;
            case 4:
                element = INSURANCE_FIREBASE + APPROVAL_CONSTANT;
                break;
            case 5:
                element = MOT_FIREBASE + APPROVAL_CONSTANT;
                break;
            case 6:
                element = LOG_BOOK_FIREBASE + APPROVAL_CONSTANT;
                break;
            case 7:
                element = PRIVATE_HIRE_VEHICLE_LICENSE + APPROVAL_CONSTANT;

        }

        return element;
    }
}
