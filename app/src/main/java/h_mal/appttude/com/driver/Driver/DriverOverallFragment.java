package h_mal.appttude.com.driver.Driver;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import h_mal.appttude.com.driver.Objects.ApprovalsObject;
import h_mal.appttude.com.driver.Objects.WholeDriverObject;
import h_mal.appttude.com.driver.R;

import static h_mal.appttude.com.driver.Global.ExecuteFragment.executeFragment;
import static h_mal.appttude.com.driver.Global.FirebaseClass.USER_FIREBASE;
import static h_mal.appttude.com.driver.MainActivity.approvalsClass;
import static h_mal.appttude.com.driver.MainActivity.archiveClass;
import static h_mal.appttude.com.driver.MainActivity.auth;
import static h_mal.appttude.com.driver.MainActivity.mDatabase;
import static h_mal.appttude.com.driver.MainActivity.viewController;


public class DriverOverallFragment extends Fragment {

    CardView driverProfile;
    CardView privateHire;
    CardView driversLicense;

    ImageView driverApr;
    ImageView privateApr ;
    ImageView driversLiApr;
    TextView privExp;
    TextView drivLiExp;

    DatabaseReference reference;

    WholeDriverObject driverProfileObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reference = mDatabase.child(USER_FIREBASE).child(auth.getCurrentUser().getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_overall, container, false);

        driverProfile = view.findViewById(R.id.driver_prof);
        privateHire = view.findViewById(R.id.private_hire);
        driversLicense = view.findViewById(R.id.drivers_license);

        driverApr = view.findViewById(R.id.approval_dp);
        privateApr = view.findViewById(R.id.approval_ph);
        driversLiApr = view.findViewById(R.id.approval_dl);

        privExp = view.findViewById(R.id.ph_button_exp);
        drivLiExp = view.findViewById(R.id.dl_button_exp);

        privExp.setVisibility(View.GONE);
        drivLiExp.setVisibility(View.GONE);

        viewController.progress(View.VISIBLE);
        reference.addListenerForSingleValueEvent(valueEventListener);

        return view;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            driverProfileObject = dataSnapshot.getValue(WholeDriverObject.class);

            if (driverProfileObject.approvalsObject != null){
                ApprovalsObject approvalsObject = driverProfileObject.getApprovalsObject();

                driverApr.setImageResource(approvalsClass.setImageResource(approvalsObject.getDriver_details_approval()));
                privateApr.setImageResource(approvalsClass.setImageResource(approvalsObject.getPrivate_hire_approval()));
                driversLiApr.setImageResource(approvalsClass.setImageResource(approvalsObject.getDriver_license_approval()));
            }

            if (driverProfileObject.driver_profile != null){
                if (driverProfileObject.getDriver_profile().private_hire != null){
                    privExp.setVisibility(View.VISIBLE);
                    privExp.setText("Expiry: " + driverProfileObject.getDriver_profile().getPrivate_hire().getPhExpiry());
                }
                if (driverProfileObject.getDriver_profile().driver_license != null){
                    drivLiExp.setVisibility(View.VISIBLE);
                    drivLiExp.setText("Expiry: " + driverProfileObject.getDriver_profile().getDriver_license().getLicenseExpiry());
                }
            }

            driverProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    executeFragment(new DriverProfileFragment());
                }
            });

            privateHire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (driverProfileObject.driver_profile == null){
                        executeFragment(new PrivateHireLicenseFragment());
                    }else {
                        archiveClass.openDialogArchive(getContext(),driverProfileObject.getDriver_profile().getPrivate_hire()
                                ,new PrivateHireLicenseFragment());
                    }

                }
            });

            driversLicense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (driverProfileObject.driver_profile == null){
                        executeFragment(new DriverLicenseFragment());
                    }else {
                        archiveClass.openDialogArchive(getContext(),driverProfileObject.getDriver_profile().getDriver_license()
                                ,new DriverLicenseFragment());
                    }
                }
            });

            viewController.progress(View.GONE);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            viewController.progress(View.GONE);
        }
    };



}
