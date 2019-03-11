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

public class VehicleOverallFragment extends Fragment {

    ImageView vehicleApr;
    ImageView insuranceApr;
    ImageView motApr;
    ImageView logbookApr;
    private ImageView privateHireCarApr;
    TextView insuranceExp;
    TextView motExp;
    private TextView privateHireExp;
    private CardView vehicleProfile;
    private CardView insurance;
    private CardView mot;
    private CardView logbook;
    private CardView privateHireCar;

    private DatabaseReference reference;
    private WholeDriverObject wholeDriverObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reference = mDatabase.child(USER_FIREBASE).child(auth.getCurrentUser().getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vehicle_overall, container, false);

        vehicleProfile = view.findViewById(R.id.vehicle_prof);
        insurance = view.findViewById(R.id.insurance);
        mot = view.findViewById(R.id.mot);
        logbook = view.findViewById(R.id.logbook);
        privateHireCar = view.findViewById(R.id.private_hire_vehicle_license);

        vehicleApr = view.findViewById(R.id.approval_vehicle);
        insuranceApr = view.findViewById(R.id.approval_insurance);
        motApr = view.findViewById(R.id.approval_mot);
        logbookApr = view.findViewById(R.id.approval_lb);
        privateHireCarApr = view.findViewById(R.id.approval_ph_car);

        insuranceExp = view.findViewById(R.id.ins_exp);
        motExp = view.findViewById(R.id.mot_exp);
        privateHireExp = view.findViewById(R.id.ph_car_exp);

        insuranceExp.setVisibility(View.GONE);
        motExp.setVisibility(View.GONE);
        privateHireExp.setVisibility(View.GONE);

        viewController.progress(View.VISIBLE);
        reference.addListenerForSingleValueEvent(valueEventListener);

        return view;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            wholeDriverObject = dataSnapshot.getValue(WholeDriverObject.class);

            if (wholeDriverObject.approvalsObject != null) {
                ApprovalsObject approvalsObject = wholeDriverObject.getApprovalsObject();

                vehicleApr.setImageResource(approvalsClass.setImageResource(approvalsObject.getVehicle_details_approval()));
                insuranceApr.setImageResource(approvalsClass.setImageResource(approvalsObject.getInsurance_details_approval()));
                motApr.setImageResource(approvalsClass.setImageResource(approvalsObject.getMot_details_approval()));
                logbookApr.setImageResource(approvalsClass.setImageResource(approvalsObject.getLog_book_approval()));
                privateHireCarApr.setImageResource(approvalsClass.setImageResource(approvalsObject.getPh_car_approval()));
            }

            if (wholeDriverObject.vehicle_profile != null){
                if (wholeDriverObject.getVehicle_profile().insurance_details != null){
                    insuranceExp.setVisibility(View.VISIBLE);
                    insuranceExp.setText("Expiry: " + wholeDriverObject.getVehicle_profile().getInsurance_details().getExpiryDate());
                }
                if (wholeDriverObject.getVehicle_profile().mot_details != null){
                    motExp.setVisibility(View.VISIBLE);
                    motExp.setText("Expiry: " + wholeDriverObject.getVehicle_profile().getMot_details().getMotExpiry());
                }
                if (wholeDriverObject.getVehicle_profile().private_hire_vehicle != null){
                    privateHireExp.setVisibility(View.VISIBLE);
                    privateHireExp.setText("Expiry: " + wholeDriverObject.getVehicle_profile().getPrivateHireVehicleObject().getPhCarExpiry());
                }
            }

            vehicleProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (wholeDriverObject.vehicle_profile == null){
                        executeFragment(new VehicleSetupFragment());
                    }else {
                        archiveClass.openDialogArchive(getContext(),wholeDriverObject.getVehicle_profile().getVehicle_details()
                                ,new VehicleSetupFragment());
                    }
                }
            });

            insurance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (wholeDriverObject.vehicle_profile == null){
                        executeFragment(new InsuranceFragment());
                    }else {
                        archiveClass.openDialogArchive(getContext(),wholeDriverObject.getVehicle_profile().getInsurance_details()
                                ,new InsuranceFragment());
                    }
                }
            });

            mot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (wholeDriverObject.vehicle_profile == null){
                        executeFragment(new MotFragment());
                    }else {
                        archiveClass.openDialogArchive(getContext(),wholeDriverObject.getVehicle_profile().getMot_details()
                                ,new MotFragment());
                    }
                }
            });

            logbook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (wholeDriverObject.vehicle_profile == null){
                        executeFragment(new logbookFragment());
                    }else {
                        archiveClass.openDialogArchive(getContext(),wholeDriverObject.getVehicle_profile().getLog_book()
                                ,new logbookFragment());
                    }
                }
            });

            privateHireCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (wholeDriverObject.vehicle_profile == null){
                        executeFragment(new PrivateHireVehicleFragment());
                    }else {
                        archiveClass.openDialogArchive(getContext(),wholeDriverObject.getVehicle_profile().getPrivateHireVehicleObject()
                                ,new PrivateHireVehicleFragment());
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
