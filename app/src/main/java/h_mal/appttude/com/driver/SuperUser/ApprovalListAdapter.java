package h_mal.appttude.com.driver.SuperUser;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import h_mal.appttude.com.driver.Archive.ArchiveFragment;
import h_mal.appttude.com.driver.Driver.DriverLicenseFragment;
import h_mal.appttude.com.driver.Driver.InsuranceFragment;
import h_mal.appttude.com.driver.Driver.MotFragment;
import h_mal.appttude.com.driver.Driver.PrivateHireLicenseFragment;
import h_mal.appttude.com.driver.Driver.PrivateHireVehicleFragment;
import h_mal.appttude.com.driver.Driver.VehicleSetupFragment;
import h_mal.appttude.com.driver.Driver.DriverProfileFragment;
import h_mal.appttude.com.driver.Driver.logbookFragment;
import h_mal.appttude.com.driver.Global.SetApprovalDialog;
import h_mal.appttude.com.driver.Objects.ApprovalsObject;
import h_mal.appttude.com.driver.Objects.ArchiveObject;
import h_mal.appttude.com.driver.Objects.WholeObject.MappedObject;
import h_mal.appttude.com.driver.Objects.WholeDriverObject;
import h_mal.appttude.com.driver.R;

import static h_mal.appttude.com.driver.Global.ExecuteFragment.executeFragment;
import static h_mal.appttude.com.driver.Global.FirebaseClass.DRIVERS_LICENSE_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.INSURANCE_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.LOG_BOOK_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.MOT_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.NO_DATE_PRESENT;
import static h_mal.appttude.com.driver.Global.FirebaseClass.PRIVATE_HIRE_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.PRIVATE_HIRE_VEHICLE_LICENSE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.VEHICLE_DETAILS_FIREBASE;
import static h_mal.appttude.com.driver.MainActivity.approvalsClass;
import static h_mal.appttude.com.driver.MainActivity.archiveClass;

public class ApprovalListAdapter extends ArrayAdapter<MappedObject> {

    private String TAG = "ApprovalListAdapter";

    String [] names = {"Driver Profile","Driver License","Private Hire","Vehicle Profile","Insurance","MOT","Logbook","P/H Vehicle"};

    MappedObject mappedObject;
    Activity activity;
    int approvalCode;

    public ApprovalListAdapter(@NonNull Activity activity, @NonNull MappedObject[] objects) {
        super(activity, 0, objects);
        this.mappedObject = objects[0];
        this.activity = activity;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(activity).inflate(
                    R.layout.approval_list_grid_item, parent, false);
        }

        approvalCode = getApprovalStatusCode(position);

        TextView textView = listItemView.findViewById(R.id.approval_text);
        textView.setText(names[position]);

        final ImageView imageView = listItemView.findViewById(R.id.approval_iv);
        imageView.setImageResource(approvalsClass.setImageResource(approvalCode));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SetApprovalDialog(approvalCode,activity,mappedObject.getUserId(),position,imageView);
            }
        });

        ImageView archiveImage = listItemView.findViewById(R.id.archive_icon);
        if (mappedObject.getWholeDriverObject().archive != null){
            Log.i(TAG, "getView: archive = " + getArchive(position,mappedObject.getWholeDriverObject().getArchive()));
            archiveImage.setVisibility(getArchive(position,mappedObject.getWholeDriverObject().getArchive()));
            archiveImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = null;
                    switch (position){
                        case 1:
                            s = DRIVERS_LICENSE_FIREBASE;
                            break;
                        case 2:
                            s = PRIVATE_HIRE_FIREBASE;
                            break;
                        case 3:
                            s = VEHICLE_DETAILS_FIREBASE;
                            break;
                        case 4:
                            s = INSURANCE_FIREBASE;
                            break;
                        case 5:
                            s = MOT_FIREBASE;
                            break;
                        case 6:
                            s = LOG_BOOK_FIREBASE;
                            break;
                        case 7:
                            s = PRIVATE_HIRE_VEHICLE_LICENSE;
                            break;

                    }
                    executeFragment(new ArchiveFragment(),mappedObject.getUserId(),s);
                }
            });
        }

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(position);
            }
        });

        listItemView.setMinimumHeight(parent.getHeight()/4);
        listItemView.setPadding((int)convertDpToPixel(9,getContext()),
                (int)convertDpToPixel(9,getContext()),
                (int)convertDpToPixel(9,getContext()),
                (int)convertDpToPixel(9,getContext()));

        return listItemView;

    }

    @Override
    public int getCount() {
        return 8;
    }

    private int getArchive(int i, ArchiveObject archiveObject){
        Object o = null;
        int visible;

        switch (i){
            case 0:
                break;
            case 1:
                o = archiveObject.driver_license;
                break;
            case 2:
                o = archiveObject.private_hire;
                break;
            case 3:
                o = archiveObject.vehicle_details;
                break;
            case 4:
                o = archiveObject.insurance_details;
                break;
            case 5:
                o = archiveObject.mot_details;
                break;
            case 6:
                o = archiveObject.log_book;
                break;
            case 7:
                o = archiveObject.private_hire_vehicle;

        }

        if (o != null){
            visible = View.VISIBLE;
        }else{
            visible = View.GONE;
        }

        return visible;
    }

    private void getFragment(int i){
        Fragment f = null;
        WholeDriverObject wholeDriverObject = mappedObject.getWholeDriverObject();
        Object o = null;
            switch (i) {
                case 0:
                    f = new DriverProfileFragment();
                    if (wholeDriverObject.driver_profile != null && wholeDriverObject.getDriver_profile().driver_profile != null) {
                        o = wholeDriverObject.getDriver_profile().getDriver_profile();
                    }
                    break;
                case 1:
                    f = new DriverLicenseFragment();
                    if (wholeDriverObject.driver_profile != null && wholeDriverObject.getDriver_profile().driver_license != null) {
                        o = wholeDriverObject.getDriver_profile().getDriver_license();
                    }
                    break;
                case 2:
                    f = new PrivateHireLicenseFragment();
                    if (wholeDriverObject.driver_profile != null && wholeDriverObject.getDriver_profile().private_hire != null) {
                        o = wholeDriverObject.getDriver_profile().getPrivate_hire();
                    }
                    break;
                case 3:
                    f = new VehicleSetupFragment();
                    if (wholeDriverObject.vehicle_profile != null && wholeDriverObject.getVehicle_profile().vehicle_details != null){
                        o = wholeDriverObject.getVehicle_profile().getVehicle_details();
                    }
                    break;
                case 4:
                    f = new InsuranceFragment();
                    if (wholeDriverObject.vehicle_profile != null && wholeDriverObject.getVehicle_profile().insurance_details != null){
                        o = wholeDriverObject.getVehicle_profile().getInsurance_details();
                    }
                    break;
                case 5:
                    f = new MotFragment();
                    if (wholeDriverObject.vehicle_profile != null && wholeDriverObject.getVehicle_profile().mot_details != null){
                        o = wholeDriverObject.getVehicle_profile().getMot_details();
                    }
                    break;
                case 6:
                    f = new logbookFragment();
                    if (wholeDriverObject.vehicle_profile != null && wholeDriverObject.getVehicle_profile().log_book != null){
                        o = wholeDriverObject.getVehicle_profile().getLog_book();
                    }
                    break;
                case 7:
                    f = new PrivateHireVehicleFragment();
                    if (wholeDriverObject.vehicle_profile != null && wholeDriverObject.getVehicle_profile().private_hire_vehicle != null){
                        o = wholeDriverObject.getVehicle_profile().getPrivateHireVehicleObject();
                    }
                    break;
            }

            if (o == null){
                executeFragment(f,mappedObject.getUserId());
            }else {
                archiveClass.openDialogArchive(getContext(),o
                        ,mappedObject.getUserId(),f);
            }

    }

    private int getApprovalStatusCode(int i){
        int statusCode = NO_DATE_PRESENT;

        if (mappedObject.getWholeDriverObject().approvalsObject != null){
            ApprovalsObject approvalsObject = mappedObject.getWholeDriverObject().getApprovalsObject();

        switch (i) {
            case 0:
                if (approvalsObject.driver_details_approval != 0) {
                    statusCode = approvalsObject.getDriver_details_approval();
                }
                break;
            case 1:
                if (approvalsObject.driver_license_approval != 0) {
                    statusCode = approvalsObject.getDriver_license_approval();
                }
                break;
            case 2:
                if (approvalsObject.private_hire_approval != 0) {
                    statusCode = approvalsObject.getPrivate_hire_approval();
                }
                break;
            case 3:
                if (approvalsObject.vehicle_details_approval != 0) {
                    statusCode = approvalsObject.getVehicle_details_approval();
                }
                break;
            case 4:
                if (approvalsObject.insurance_details_approval != 0) {
                    statusCode = approvalsObject.getInsurance_details_approval();
                }
                break;
            case 5:
                if (approvalsObject.mot_details_approval != 0) {
                    statusCode = approvalsObject.getMot_details_approval();
                }
                break;
            case 6:
                if (approvalsObject.log_book_approval != 0) {
                    statusCode = approvalsObject.getLog_book_approval();
                }
                break;
            case 7:
                if (approvalsObject.private_hire_vehicle_approval != 0) {
                    statusCode = approvalsObject.getPh_car_approval();
                }
                break;
            }
        }

        return statusCode;
    }

    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}
