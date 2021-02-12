package h_mal.appttude.com.driver.Driver;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import h_mal.appttude.com.driver.Global.DateDialog;
import h_mal.appttude.com.driver.Objects.DriversLicenseObject;
import h_mal.appttude.com.driver.Objects.InsuranceObject;
import h_mal.appttude.com.driver.Objects.LogbookObject;
import h_mal.appttude.com.driver.Objects.MotObject;
import h_mal.appttude.com.driver.Objects.VehicleProfileObject;
import h_mal.appttude.com.driver.R;

import static h_mal.appttude.com.driver.Global.ExecuteFragment.UPLOAD_NEW;
import static h_mal.appttude.com.driver.Global.ExecuteFragment.executeFragment;
import static h_mal.appttude.com.driver.Global.FirebaseClass.APPROVAL_CONSTANT;
import static h_mal.appttude.com.driver.Global.FirebaseClass.APPROVAL_PENDING;
import static h_mal.appttude.com.driver.Global.FirebaseClass.DRIVERS_LICENSE_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.INSURANCE_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.LOG_BOOK_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.MOT_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.USER_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.VEHICLE_DETAILS_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.VEHICLE_FIREBASE;
import static h_mal.appttude.com.driver.MainActivity.approvalsClass;
import static h_mal.appttude.com.driver.MainActivity.archiveClass;
import static h_mal.appttude.com.driver.MainActivity.auth;
import static h_mal.appttude.com.driver.MainActivity.fragmentManager;
import static h_mal.appttude.com.driver.MainActivity.mDatabase;
import static h_mal.appttude.com.driver.MainActivity.viewController;


public class VehicleSetupFragment extends Fragment {

    private String TAG = this.getClass().getSimpleName();

    EditText reg;
    EditText make;
    EditText model;
    EditText color;
    EditText keeperName;
    EditText address;
    EditText postcode;
    EditText startDate;
    CheckBox seized;
    Button Submit;

    DatabaseReference ref;

    VehicleProfileObject vehicleProfileObject;
    Boolean uploadNew;
    String UID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uploadNew = false;

        if (getArguments() != null){
            Log.i(TAG, "onCreate: args = args exist");
            if (getArguments().containsKey("user_id")){
                UID = getArguments().getString("user_id");
            }else {
                UID = auth.getCurrentUser().getUid();
            }
            if (getArguments().containsKey(UPLOAD_NEW)){
                uploadNew = true;
            }
        }else{
            UID = auth.getCurrentUser().getUid();
        }

        ref = mDatabase.child(USER_FIREBASE).child(UID).child(VEHICLE_FIREBASE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vehicle_setup, container, false);

        reg = view.findViewById(R.id.reg);
        make = view.findViewById(R.id.make);
        model = view.findViewById(R.id.model);
        color = view.findViewById(R.id.colour);
        keeperName = view.findViewById(R.id.keeper_name);
        address = view.findViewById(R.id.address);
        postcode = view.findViewById(R.id.postcode);
        startDate = view.findViewById(R.id.start_date);
        seized = view.findViewById(R.id.seized);
        Submit = view.findViewById(R.id.submit_vehicle);

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dateDialog = new DateDialog(getContext());
                dateDialog.init(startDate);
            }
        });

        viewController.progress(View.VISIBLE);
        ref.addListenerForSingleValueEvent(valueEventListener);

        Submit.setOnClickListener(submitOnClickListener);

        return view;
    }

    ValueEventListener valueEventListener = new ValueEventListener(){

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            viewController.progress(View.GONE);
            try{
                vehicleProfileObject = dataSnapshot.child(VEHICLE_DETAILS_FIREBASE).getValue(VehicleProfileObject.class);
            }catch (Exception e){
                Log.e(TAG, "onDataChange: ", e);
            }finally {
                if (vehicleProfileObject != null){
                    if(!uploadNew) {
                        reg.setText(vehicleProfileObject.getReg());
                        make.setText(vehicleProfileObject.getMake());
                        model.setText(vehicleProfileObject.getModel());
                        color.setText(vehicleProfileObject.getColour());
                        keeperName.setText(vehicleProfileObject.getKeeperName());
                        address.setText(vehicleProfileObject.getKeeperAddress());
                        postcode.setText(vehicleProfileObject.getKeeperPostCode());
                        startDate.setText(vehicleProfileObject.getStartDate());
                        seized.setChecked(vehicleProfileObject.isSeized());
                    }
                }
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            viewController.progress(View.GONE);
        }
    };

    View.OnClickListener submitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String regString =  reg.getText().toString().trim();
            String makeString =  make.getText().toString().trim();
            String modelString =  model.getText().toString().trim();
            String colourString =  color.getText().toString().trim();
            String keeperNameStrin =  keeperName.getText().toString().trim();
            String addressString =  address.getText().toString().trim();
            String postcodeString =  postcode.getText().toString().trim();
            String driverForename =  startDate.getText().toString().trim();

            if (!TextUtils.isEmpty(regString)
                    &&!TextUtils.isEmpty(makeString)
                    &&!TextUtils.isEmpty(modelString)
                    &&!TextUtils.isEmpty(colourString)
                    &&!TextUtils.isEmpty(keeperNameStrin)
                    &&!TextUtils.isEmpty(addressString)
                    &&!TextUtils.isEmpty(postcodeString)
                    &&!TextUtils.isEmpty(driverForename)) {

                if (uploadNew){
                    archiveClass.archiveRecord(UID,VEHICLE_DETAILS_FIREBASE,vehicleProfileObject);
                }

                VehicleProfileObject vehicleProfileObject = new VehicleProfileObject(
                        regString ,makeString ,modelString ,colourString ,keeperNameStrin ,addressString ,postcodeString ,driverForename,
                        seized.isChecked()
                );

                viewController.progress(View.VISIBLE);
                ref.child(VEHICLE_DETAILS_FIREBASE).setValue(vehicleProfileObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            approvalsClass.setStatusCode(UID,VEHICLE_DETAILS_FIREBASE + APPROVAL_CONSTANT,APPROVAL_PENDING);
                            approvalsClass.setStatusCode(UID,MOT_FIREBASE + APPROVAL_CONSTANT,APPROVAL_PENDING);
                            approvalsClass.setStatusCode(UID,INSURANCE_FIREBASE + APPROVAL_CONSTANT,APPROVAL_PENDING);
                            approvalsClass.setStatusCode(UID,LOG_BOOK_FIREBASE + APPROVAL_CONSTANT,APPROVAL_PENDING);
                            fragmentManager.popBackStack();
                        }else{
                            Toast.makeText(getContext(), "Upload Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                        viewController.progress(View.GONE);
                    }
                });

            }else {
                if (TextUtils.isEmpty(regString)){reg.setError("Field required");}
                if (TextUtils.isEmpty(makeString)){make.setError("Field required");}
                if (TextUtils.isEmpty(modelString)){model.setError("Field required");}
                if (TextUtils.isEmpty(colourString)){color.setError("Field required");}
                if (TextUtils.isEmpty(keeperNameStrin)){keeperName.setError("Field required");}
                if (TextUtils.isEmpty(addressString)){address.setError("Field required");}
                if (TextUtils.isEmpty(postcodeString)){postcode.setError("Field required");}
                if (TextUtils.isEmpty(driverForename)){startDate.setError("Field required");}

            }

        }

    };

}
