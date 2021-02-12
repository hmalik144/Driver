package h_mal.appttude.com.driver.Global;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import h_mal.appttude.com.driver.Objects.ApprovalsObject;
import h_mal.appttude.com.driver.Objects.WholeDriverObject;
import h_mal.appttude.com.driver.R;

import static h_mal.appttude.com.driver.Global.FirebaseClass.APPROVAL_DENIED;
import static h_mal.appttude.com.driver.Global.FirebaseClass.APPROVAL_PENDING;
import static h_mal.appttude.com.driver.Global.FirebaseClass.APPROVED;
import static h_mal.appttude.com.driver.Global.FirebaseClass.NO_DATE_PRESENT;
import static h_mal.appttude.com.driver.Global.FirebaseClass.USER_APPROVALS;
import static h_mal.appttude.com.driver.Global.FirebaseClass.USER_FIREBASE;
import static h_mal.appttude.com.driver.MainActivity.mDatabase;
import static h_mal.appttude.com.driver.MainActivity.printObjectAsJson;

public class ApprovalsClass {

    public ApprovalsClass() {
    }

    public static int mode(int[] array) {
        printObjectAsJson("modeArrays",array);
        int mode = array[0];
        int maxCount = 0;
        if (matchedArray(array,3)){
            return 3;
        }else if (matchedArray(array,0)){
            return 0;
        }else {
            for (int i = 0; i < array.length; i++) {
                int value = array[i];
                int count = 1;
                for (int j = 0; j < array.length; j++) {
                    if (array[j] == value) count++;
                    if (count > maxCount) {
                        mode = value;
                        maxCount = count;
                    }
                }
            }
            if (mode == 3){
                return 1;
            }
        }
        return mode;
    }

    private static boolean matchedArray (int [] array, int match){
        for (int i : array){
            if (i != match){
                return false;
            }
        }

        return true;
    }

    public int getOverApprovalStatusCode(WholeDriverObject wholeDriverObject){

        if (wholeDriverObject.approvalsObject != null){
            ApprovalsObject approvalsObject = wholeDriverObject.getApprovalsObject();

            int[] ints = new int[]{approvalsObject.getDriver_details_approval(),
                            approvalsObject.getDriver_license_approval(),
                            approvalsObject.getPrivate_hire_approval(),
                            approvalsObject.getVehicle_details_approval(),
                            approvalsObject.getInsurance_details_approval(),
                            approvalsObject.getMot_details_approval(),
                            approvalsObject.getLog_book_approval(),
                            approvalsObject.getPh_car_approval()};


            return setImageResource(mode(ints));
        }

        return setImageResource(NO_DATE_PRESENT);
    }

    public void setStatusCode(String userId,String approvalNameString,int status){

            if (!approvalNameString.equals("")) {
                mDatabase.child(USER_FIREBASE).child(userId).child(USER_APPROVALS).child(approvalNameString)
                        .setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        } else {

                        }
                    }
                });
            }


    }

    public int setImageResource(int statusCode){
        int imageResource;

        switch (statusCode){
            case APPROVAL_PENDING:
                imageResource = R.drawable.pending;
                break;
            case APPROVAL_DENIED:
                imageResource = R.drawable.denied;
                break;
            case APPROVED:
                imageResource = R.drawable.approved;
                break;
            default:
                imageResource = R.drawable.zero;
                break;
        }

        return imageResource;
    }
}
