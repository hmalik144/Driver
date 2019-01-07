package h_mal.appttude.com.driver;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import h_mal.appttude.com.driver.Objects.DriverProfileObject;
import h_mal.appttude.com.driver.Objects.DriversLicenseObject;
import h_mal.appttude.com.driver.Objects.PrivateHireObject;

import static h_mal.appttude.com.driver.FirebaseClass.*;
import static h_mal.appttude.com.driver.MainActivity.auth;
import static h_mal.appttude.com.driver.MainActivity.executeFragment;
import static h_mal.appttude.com.driver.MainActivity.fragmentManager;
import static h_mal.appttude.com.driver.MainActivity.getDateStamp;
import static h_mal.appttude.com.driver.MainActivity.mDatabase;


public class driverProfileFragment extends Fragment {

    private String TAG = this.getClass().getSimpleName();

    ImageView driverPic;
    TextView addPic;
    EditText forenames;
    EditText address;
    EditText postcode;
    EditText dob;
    EditText ni;
    TextView privateHire;
    TextView retrievedPH;
    EditText dateFirst;
    TextView driverLi;
    TextView retrievedDl;
    Button submit_driver;

    Uri filePath;
    Uri picUri;

    DatabaseReference driverProfileReference;

    PrivateHireObject privateHireObject;
    DriverProfileObject driverProfileObject;
    DriversLicenseObject driversLicenseObject;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        driverProfileReference = mDatabase.child(USER_FIREBASE).child(auth.getCurrentUser().getUid())
                .child(DRIVER_FIREBASE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_profile, container, false);

        driverPic = view.findViewById(R.id.driver_pic);
        addPic = view.findViewById(R.id.add_driver_pic);
        forenames = view.findViewById(R.id.names);
        address = view.findViewById(R.id.address);
        postcode  = view.findViewById(R.id.postcode);
        dob = view.findViewById(R.id.dob);
        ni = view.findViewById(R.id.ni_number);
        privateHire = view.findViewById(R.id.private_hire);
        retrievedPH = view.findViewById(R.id.retrievedPH);
        dateFirst = view.findViewById(R.id.date_first);
        driverLi = view.findViewById(R.id.drivers_li);
        retrievedDl = view.findViewById(R.id.retrieved_dl);
        submit_driver = view.findViewById(R.id.submit_driver);

        driverPic.setVisibility(View.GONE);

        driverProfileReference.addListenerForSingleValueEvent(valueEventListener);

        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelectorDialog imageSelectorDialog = new ImageSelectorDialog(getContext());
                imageSelectorDialog.setImageName("driver_pic"+getDateStamp());
                imageSelectorDialog.show();
            }
        });

        privateHire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeFragment(new PrivateHireLicenseFragment());
            }
        });

        driverLi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeFragment(new DriverLicenseFragment());
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dateDialog = new DateDialog(getContext());
                dateDialog.init(dob);
            }
        });

        dateFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dateDialog = new DateDialog(getContext());
                dateDialog.init(dateFirst);
            }
        });

        submit_driver.setOnClickListener(submitOnClickListener);


        return view;
    }

    ValueEventListener valueEventListener = new ValueEventListener(){

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            try{
                privateHireObject = dataSnapshot.child(PRIVATE_HIRE_FIREBASE).getValue(PrivateHireObject.class);
            }catch (Exception e){
                Log.e(TAG, "onDataChange: ", e);
            }finally {
                if (privateHireObject != null){

                    retrievedPH.setText("Private Hire License no.: " +privateHireObject.getPhNumber()
                            +"\nPrivate Hire License expiry: " + privateHireObject.getPhExpiry());
                }
            }

            try {
                driverProfileObject = dataSnapshot.child(DRIVER_DETAILS_FIREBASE).getValue(DriverProfileObject.class);
            }catch (Exception e){
                Log.e(TAG, "onDataChange: ", e);
            }finally {
                if (driverProfileObject != null){
                    forenames.setText(driverProfileObject.getForenames());
                    address.setText(driverProfileObject.getAddress());
                    postcode.setText(driverProfileObject.getPostcode());
                    dob.setText(driverProfileObject.getDob());
                    dateFirst.setText(driverProfileObject.getDateFirst());
                    ni.setText(driverProfileObject.getNi());
                    driverPic.setVisibility(View.VISIBLE);
                    Picasso.get().load(driverProfileObject.getDriverPic()).into(driverPic);
                    picUri = Uri.parse(driverProfileObject.getDriverPic());

                }else {
                    driverPic.setVisibility(View.GONE);
                }
            }

            try{
                driversLicenseObject = dataSnapshot.child(DRIVERS_LICENSE_FIREBASE).getValue(DriversLicenseObject.class);
            }catch (Exception e){
                Log.e(TAG, "onDataChange: ", e);
            }finally {
                if (driversLicenseObject != null){
                    retrievedDl.setText("Driving License no.: " +driversLicenseObject.getLicenseNumber()
                            +"\nDriving License expiry: " + driversLicenseObject.getLicenseExpiry());
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    View.OnClickListener submitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String driverForename = forenames.getText().toString().trim();
            String AddressString = address.getText().toString().trim();
            String postCodeString = postcode.getText().toString().trim();
            String dobString = dob.getText().toString().trim();
            String niString = ni.getText().toString().trim();
            String dateFirstString = dateFirst.getText().toString().trim();

            if (    !TextUtils.isEmpty(driverForename) &&
                    !TextUtils.isEmpty(AddressString) &&
                    !TextUtils.isEmpty(postCodeString) &&
                    !TextUtils.isEmpty(dobString) &&
                    !TextUtils.isEmpty(niString) &&
                    !TextUtils.isEmpty(dateFirstString)){
                if (filePath != null){
                    Log.i(TAG, "onClick: new Image uploaded");
                    new FirebaseClass(getContext(),filePath,new FirebaseClass.Response(){
                        @Override
                        public void processFinish(Uri output) {
                            Log.i(TAG, "processFinish: ");
                            if (output != null){
                                picUri = output;
                                writeDriverToDb();
                                fragmentManager.popBackStack();
                            }

                        }
                    }).uploadImage(DRIVERS_LICENSE_FIREBASE,DRIVERS_LICENSE_FIREBASE + getDateStamp());
                }else{
                    Log.i(TAG, "onClick: pushing with same image");
                    writeDriverToDb();
                }

            }else {
                if (TextUtils.isEmpty(driverForename)){
                    forenames.setError("Field required");
                }
                if (TextUtils.isEmpty(AddressString)){
                    address.setError("Field required");
                }
                if (TextUtils.isEmpty(postCodeString)){
                    postcode.setError("Field required");
                }
                if (TextUtils.isEmpty(dobString)){
                    dob.setError("Field required");
                }
                if (TextUtils.isEmpty(niString)){
                    ni.setError("Field required");
                }
                if (TextUtils.isEmpty(dateFirstString)){
                    dateFirst.setError("Field required");
                }
                if (picUri == null){
                    Toast.makeText(getContext(), getString(R.string.image_required), Toast.LENGTH_SHORT).show();
                }
            }

        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        new ImageSelectorResults(new ImageSelectorResults.FilepathResponse() {
            @Override
            public void processFinish(Uri output) {
                filePath = output;
            }
        }).Results(getActivity(),requestCode, resultCode, data,
                filePath,driverPic);
        driverPic.setVisibility(View.VISIBLE);

    }

    private void writeDriverToDb(){

        String forenameText = forenames.getText().toString().trim();
        String addressText = address.getText().toString().trim();
        String postcodeText = postcode.getText().toString().trim();
        String dobText = dob.getText().toString().trim();
        String niText = ni.getText().toString().trim();
        String datefirstText = dateFirst.getText().toString().trim();

        DriverProfileObject driverProfileObject = new DriverProfileObject(picUri.toString(),forenameText,
                addressText,postcodeText,dobText,niText,datefirstText);

        driverProfileReference.child(DRIVER_DETAILS_FIREBASE).setValue(driverProfileObject);
    }
}
