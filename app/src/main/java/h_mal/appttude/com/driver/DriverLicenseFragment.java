package h_mal.appttude.com.driver;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import h_mal.appttude.com.driver.Objects.DriversLicenseObject;
import h_mal.appttude.com.driver.Objects.PrivateHireObject;

import static h_mal.appttude.com.driver.FirebaseClass.DRIVERS_LICENSE_FIREBASE;
import static h_mal.appttude.com.driver.FirebaseClass.DRIVER_FIREBASE;
import static h_mal.appttude.com.driver.FirebaseClass.PRIVATE_HIRE_FIREBASE;
import static h_mal.appttude.com.driver.FirebaseClass.USER_FIREBASE;
import static h_mal.appttude.com.driver.ImageSelectorDialog.CAMERA_REQUEST;
import static h_mal.appttude.com.driver.ImageSelectorDialog.MY_CAMERA_PERMISSION_CODE;
import static h_mal.appttude.com.driver.MainActivity.auth;
import static h_mal.appttude.com.driver.MainActivity.fragmentManager;
import static h_mal.appttude.com.driver.MainActivity.getDateStamp;
import static h_mal.appttude.com.driver.MainActivity.mDatabase;

public class DriverLicenseFragment extends Fragment {

    private String TAG = this.getClass().getSimpleName();

    private ImageView imageView;

    EditText licenseNo;
    EditText expiry;

    public Uri filePath;
    public Uri picUri;

    String li_numberString;
    String li_exprString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_license, container, false);

        imageView = view.findViewById(R.id.driversli_img);

        DatabaseReference reference = mDatabase.child(USER_FIREBASE).child(auth.getCurrentUser().getUid())
                .child(DRIVER_FIREBASE).child(DRIVERS_LICENSE_FIREBASE);

        reference.addListenerForSingleValueEvent(valueEventListener);

        TextView uploadLic = view.findViewById(R.id.upload_lic);

        licenseNo = view.findViewById(R.id.lic_no);
        expiry = view.findViewById(R.id.lic_expiry);

        expiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dateDialog = new DateDialog(getContext());
                dateDialog.init(expiry);
            }
        });

        Button submit = view.findViewById(R.id.submit);
        submit.setOnClickListener(submitOnClickListener);

        uploadLic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelectorDialog imageSelectorDialog = new ImageSelectorDialog(getContext());
                imageSelectorDialog.setImageName("drivers_license");
                imageSelectorDialog.show();
            }
        });

        return view;
    }

    View.OnClickListener submitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            li_numberString = licenseNo.getText().toString().trim();
            li_exprString = expiry.getText().toString().trim();

            if (!TextUtils.isEmpty(li_numberString) &&
                    !TextUtils.isEmpty(li_exprString)){
                if (filePath != null){
                    Log.i(TAG, "onClick: new Image uploaded");
                    new FirebaseClass(getContext(),filePath,new FirebaseClass.Response(){
                        @Override
                        public void processFinish(Uri output) {
                            Log.i(TAG, "processFinish: ");
                            if (output != null){
                                picUri = output;
                                publishObject();
                                fragmentManager.popBackStack();
                            }

                        }
                    }).uploadImage(DRIVERS_LICENSE_FIREBASE,DRIVERS_LICENSE_FIREBASE + getDateStamp());
                }else{
                    Log.i(TAG, "onClick: pushing with same image");
                    publishObject();
                }

            }else {
                if (TextUtils.isEmpty(li_numberString)){
                    licenseNo.setError("Field required");
                }
                if (TextUtils.isEmpty(li_exprString)){
                    expiry.setError("Field required");
                }
                if (picUri == null){
                    Toast.makeText(getContext(), getString(R.string.image_required), Toast.LENGTH_SHORT).show();
                }
            }

        }

    };

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            DriversLicenseObject driversLicenseObject = null;
            try{
                driversLicenseObject = dataSnapshot.getValue(DriversLicenseObject.class);
            }catch (Exception e){
                Log.e(TAG, "onDataChange: ", e);
            }finally {
                if (driversLicenseObject != null){
                    picUri = Uri.parse(driversLicenseObject.getLicenseImageString());
                    li_numberString = driversLicenseObject.getLicenseNumber();
                    li_exprString = driversLicenseObject.getLicenseExpiry();

                    licenseNo.setText(li_numberString);
                    expiry.setText(li_exprString);
                    Picasso.get()
                            .load(picUri)
                            .placeholder(R.mipmap.ic_launcher_round)
                            .into(imageView);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void publishObject(){

        DriversLicenseObject driversLicenseObject = new DriversLicenseObject(picUri.toString(),li_numberString,li_exprString);

        mDatabase.child(USER_FIREBASE).child(auth.getCurrentUser().getUid()).child(DRIVER_FIREBASE).child(DRIVERS_LICENSE_FIREBASE)
                .setValue(driversLicenseObject).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Log.i(TAG, "onComplete: publish = " + task.isSuccessful());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        new ImageSelectorResults(new ImageSelectorResults.FilepathResponse() {
            @Override
            public void processFinish(Uri output) {
                filePath = output;
            }
        }).Results(getActivity(),requestCode, resultCode, data,
                filePath,imageView);

    }

}
