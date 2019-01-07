package h_mal.appttude.com.driver;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import h_mal.appttude.com.driver.Objects.PrivateHireObject;

import static h_mal.appttude.com.driver.FirebaseClass.DRIVERS_LICENSE_FIREBASE;
import static h_mal.appttude.com.driver.FirebaseClass.DRIVER_FIREBASE;
import static h_mal.appttude.com.driver.FirebaseClass.PRIVATE_HIRE_FIREBASE;
import static h_mal.appttude.com.driver.FirebaseClass.USER_FIREBASE;
import static h_mal.appttude.com.driver.MainActivity.auth;
import static h_mal.appttude.com.driver.MainActivity.executeFragment;
import static h_mal.appttude.com.driver.MainActivity.fragmentManager;
import static h_mal.appttude.com.driver.MainActivity.getDateStamp;
import static h_mal.appttude.com.driver.MainActivity.mDatabase;


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
    TextView mot;
    TextView retrievedmot;
    TextView insurance;
    TextView retrievedIns;
    TextView logbook;
    TextView logbook_retrieved;
    Button Submit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        mot = view.findViewById(R.id.mot);
        retrievedmot = view.findViewById(R.id.retrievedmot);
        insurance = view.findViewById(R.id.insurance);
        retrievedIns = view.findViewById(R.id.insurance_retrieved);
        logbook = view.findViewById(R.id.log_book);
        logbook_retrieved = view.findViewById(R.id.log_book_retrieved);
        Submit = view.findViewById(R.id.submit_vehicle);

        initiateViewsFromFb();

        mot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeFragment(new MotFragment());
            }
        });

        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeFragment(new InsuranceFragment());
            }
        });

        logbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeFragment(new logbookFragment());
            }
        });

        Submit.setOnClickListener(submitOnClickListener);

        return view;
    }

    private void initiateViewsFromFb() {
        //todo: retrieve object and set data in fields
    }

    View.OnClickListener submitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO: get strings from views
            String regString =  reg.getText().toString().trim();
            String makeString =  make.getText().toString().trim();
            String modelString =  model.getText().toString().trim();
            String colourString =  color.getText().toString().trim();
            String keeperNameStrin =  keeperName.getText().toString().trim();
            String addressString =  address.getText().toString().trim();
            String postcodeString =  postcode.getText().toString().trim();
            String driverForename =  startDate.getText().toString().trim();

            //TODO: validate the strings
//            if (    !TextUtils.isEmpty(driverForename) &&
//                    !TextUtils.isEmpty(AddressString) &&
//                    !TextUtils.isEmpty(postCodeString) &&
//                    !TextUtils.isEmpty(dobString) &&
//                    !TextUtils.isEmpty(niString) &&
//                    !TextUtils.isEmpty(dateFirstString)){

//                    Log.i(TAG, "onClick: new Image uploaded");
//                    new FirebaseClass(getContext(),filePath,new FirebaseClass.Response(){
//                        @Override
//                        public void processFinish(Uri output) {
//                            Log.i(TAG, "processFinish: ");
//                            if (output != null){
//                                picUri = output;
//                                writeDriverToDb();
//                                fragmentManager.popBackStack();
//                            }
//
//                        }
//                    }).uploadImage(DRIVERS_LICENSE_FIREBASE,DRIVERS_LICENSE_FIREBASE + getDateStamp());
//                    Log.i(TAG, "onClick: pushing with same image");
//                    writeDriverToDb();

            //todo: set error if invalid
//            }else {
//                if (TextUtils.isEmpty(driverForename)){
//                    forenames.setError("Field required");
//                }
//                if (TextUtils.isEmpty(AddressString)){
//                    address.setError("Field required");
//                }
//                if (TextUtils.isEmpty(postCodeString)){
//                    postcode.setError("Field required");
//                }
//                if (TextUtils.isEmpty(dobString)){
//                    dob.setError("Field required");
//                }
//                if (TextUtils.isEmpty(niString)){
//                    ni.setError("Field required");
//                }
//                if (TextUtils.isEmpty(dateFirstString)){
//                    dateFirst.setError("Field required");
//                }
//            }

        }

    };

    private void publishObject(){
        //TODO: publish vehicle object
//        PrivateHireObject privateHireObject = new PrivateHireObject(picUri.toString(),Ph_numberString,Ph_exprString);
//
//        mDatabase.child(USER_FIREBASE).child(auth.getCurrentUser().getUid()).child(DRIVER_FIREBASE).child(PRIVATE_HIRE_FIREBASE)
//                .setValue(privateHireObject).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                Log.i(TAG, "onComplete: publish = " + task.isSuccessful());
//            }
//        });
    }

}
