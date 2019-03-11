package h_mal.appttude.com.driver.Driver;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import h_mal.appttude.com.driver.Global.DateDialog;
import h_mal.appttude.com.driver.Global.FirebaseClass;
import h_mal.appttude.com.driver.Global.ImageSelectorDialog;
import h_mal.appttude.com.driver.Global.ImageSelectorResults;
import h_mal.appttude.com.driver.Global.ViewController;
import h_mal.appttude.com.driver.Objects.DriverProfileObject;
import h_mal.appttude.com.driver.Objects.DriversLicenseObject;
import h_mal.appttude.com.driver.Objects.PrivateHireObject;
import h_mal.appttude.com.driver.Objects.UserObject;
import h_mal.appttude.com.driver.R;

import static h_mal.appttude.com.driver.Global.ExecuteFragment.executeFragment;
import static h_mal.appttude.com.driver.Global.FirebaseClass.*;
import static h_mal.appttude.com.driver.MainActivity.approvalsClass;
import static h_mal.appttude.com.driver.MainActivity.auth;
import static h_mal.appttude.com.driver.MainActivity.fragmentManager;
import static h_mal.appttude.com.driver.MainActivity.getDateStamp;
import static h_mal.appttude.com.driver.MainActivity.loadImage;
import static h_mal.appttude.com.driver.MainActivity.mDatabase;
import static h_mal.appttude.com.driver.MainActivity.viewController;


public class DriverProfileFragment extends Fragment {

    private String TAG = this.getClass().getSimpleName();

    ImageView driverPic;
    ProgressBar pb;
    TextView addPic;
    EditText forenames;
    EditText address;
    EditText postcode;
    EditText dob;
    EditText ni;
    EditText dateFirst;
    Button submit_driver;

    Uri filePath;
    Uri picUri;

    DatabaseReference driverProfileReference;
    String UID;
    DriverProfileObject driverProfileObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null){
            UID = getArguments().getString("user_id");
        }else{
            UID = auth.getCurrentUser().getUid();
        }

        driverProfileReference = mDatabase.child(USER_FIREBASE).child(UID)
                .child(DRIVER_FIREBASE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_profile, container, false);

        driverPic = view.findViewById(R.id.driver_pic);
        pb = view.findViewById(R.id.pb_dp);
        addPic = view.findViewById(R.id.add_driver_pic);
        forenames = view.findViewById(R.id.names);
        address = view.findViewById(R.id.address);
        postcode  = view.findViewById(R.id.postcode);
        dob = view.findViewById(R.id.dob);
        ni = view.findViewById(R.id.ni_number);
        dateFirst = view.findViewById(R.id.date_first);
        submit_driver = view.findViewById(R.id.submit_driver);

        viewController.progress(View.VISIBLE);
        driverProfileReference.addListenerForSingleValueEvent(valueEventListener);

        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelectorDialog imageSelectorDialog = new ImageSelectorDialog(getContext());
                imageSelectorDialog.setImageName("driver_pic"+getDateStamp());
                imageSelectorDialog.show();
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
            viewController.progress(View.GONE);

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


                    Picasso.get().load(driverProfileObject.getDriverPic())
                            .into(loadImage(pb,driverPic));
                    picUri = Uri.parse(driverProfileObject.getDriverPic());
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

                if (filePath == null && picUri == null){
                    Toast.makeText(getContext(), "No Driver image", Toast.LENGTH_SHORT).show();
                    viewController.progress(View.GONE);
                }else {
                    viewController.progress(View.VISIBLE);
                    if (filePath != null){

                        new FirebaseClass(getContext(),filePath,new FirebaseClass.Response(){
                            @Override
                            public void processFinish(Uri output) {
                                Log.i(TAG, "processFinish: ");
                                if (output != null){
                                    picUri = output;
                                    writeDriverToDb();

                                }else {
                                    viewController.progress(View.GONE);
                                }

                            }
                        }).uploadImage(DRIVERS_LICENSE_FIREBASE,DRIVERS_LICENSE_FIREBASE + getDateStamp());
                    }else{
                        Log.i(TAG, "onClick: pushing with same image");
                        writeDriverToDb();
                    }
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
            }

        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        new ImageSelectorResults().Results(getActivity(),requestCode, resultCode, data,
                filePath,driverPic,new ImageSelectorResults.FilepathResponse() {
                    @Override
                    public void processFinish(Uri output) {
                        filePath = output;
                    }
                });


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

        if (UID.equals(auth.getCurrentUser().getUid())){
            UserProfileChangeRequest.Builder profileUpdatesBuilder = new UserProfileChangeRequest.Builder();
            profileUpdatesBuilder.setPhotoUri(picUri);
            UserProfileChangeRequest profileUpdates = profileUpdatesBuilder.build();

            auth.getCurrentUser().updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User profile updated.");
                                viewController.reloadDrawer();
                                mDatabase.child(USER_FIREBASE).child(auth.getCurrentUser().getUid()).child("user_details")
                                        .setValue(new UserObject(auth.getCurrentUser().getDisplayName(),auth.getCurrentUser().getEmail(),picUri.toString()));
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: ", e);
                        }
                    });

        }

        driverProfileReference.child(DRIVER_DETAILS_FIREBASE).setValue(driverProfileObject)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        approvalsClass.setStatusCode(UID,DRIVER_DETAILS_FIREBASE + APPROVAL_CONSTANT,APPROVAL_PENDING);
                    }
                    viewController.progress(View.GONE);
                    fragmentManager.popBackStack();
                }
            });
    }
}
