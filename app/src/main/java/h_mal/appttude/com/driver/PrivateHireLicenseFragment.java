package h_mal.appttude.com.driver;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.view.Window;
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

import h_mal.appttude.com.driver.Objects.PrivateHireObject;

import static h_mal.appttude.com.driver.FirebaseClass.*;
import static h_mal.appttude.com.driver.ImageSelectorDialog.CAMERA_REQUEST;
import static h_mal.appttude.com.driver.ImageSelectorDialog.MY_CAMERA_PERMISSION_CODE;
import static h_mal.appttude.com.driver.MainActivity.fragmentManager;
import static h_mal.appttude.com.driver.MainActivity.getDateStamp;
import static h_mal.appttude.com.driver.MainActivity.mDatabase;
import static h_mal.appttude.com.driver.MainActivity.auth;

public class PrivateHireLicenseFragment extends Fragment {

    private String TAG = this.getClass().getSimpleName();

    private ImageView imageView;
    String fname;

    EditText phNo;
    EditText phExpiry;

    public Uri filePath;

    public Uri picUri;
    String Ph_numberString;
    String Ph_exprString;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_private_hire_license, container, false);

        DatabaseReference reference = mDatabase.child(USER_FIREBASE).child(auth.getCurrentUser().getUid())
                .child(DRIVER_FIREBASE).child(PRIVATE_HIRE_FIREBASE);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PrivateHireObject privateHireObject = null;
                try{
                    privateHireObject = dataSnapshot.getValue(PrivateHireObject.class);
                }catch (Exception e){
                    Log.e(TAG, "onDataChange: ", e);
                }finally {
                    if (privateHireObject != null){
                        picUri = Uri.parse(privateHireObject.getPhImageString());
                        Ph_numberString = privateHireObject.getPhNumber();
                        Ph_exprString = privateHireObject.getPhExpiry();

                        phNo.setText(Ph_numberString);
                        phExpiry.setText(Ph_exprString);
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
        });

        TextView uploadPH = view.findViewById(R.id.uploadphlic);
        imageView = view.findViewById(R.id.imageView2);

        phNo = view.findViewById(R.id.ph_no);
        phExpiry  = view.findViewById(R.id.ph_expiry);

        phExpiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dateDialog = new DateDialog(getContext());
                dateDialog.init(phExpiry);
            }
        });

        Button submit  = view.findViewById(R.id.submit);

        uploadPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelectorDialog imageSelectorDialog = new ImageSelectorDialog(getContext());
                imageSelectorDialog.setImageName("private_hire");
                imageSelectorDialog.show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ph_numberString = phNo.getText().toString().trim();
                Ph_exprString = phExpiry.getText().toString().trim();

                //validation for data then submit

                if (!TextUtils.isEmpty(Ph_numberString) &&
                        !TextUtils.isEmpty(Ph_exprString)){
                    if (filePath != null){
                        Log.i(TAG, "onClick: new Image uploaded");
                        new FirebaseClass(getContext(),filePath,new Response(){
                            @Override
                            public void processFinish(Uri output) {
                                Log.i(TAG, "processFinish: ");
                                if (output != null){
                                    picUri = output;
                                    publishObject();
                                    fragmentManager.popBackStack();
                                }

                            }
                        }).uploadImage(PRIVATE_HIRE_FIREBASE,PRIVATE_HIRE_FIREBASE + getDateStamp());
                    }else{
                        Log.i(TAG, "onClick: pushing with same image");
                        publishObject();
                    }

                }else {
                    if (TextUtils.isEmpty(Ph_numberString)){
                        phNo.setError("Field required");
                    }
                    if (TextUtils.isEmpty(Ph_exprString)){
                        phExpiry.setError("Field required");
                    }
                    if (picUri == null){
                        Toast.makeText(getContext(), getString(R.string.image_required), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        return view;
    }

    private void publishObject(){

        PrivateHireObject privateHireObject = new PrivateHireObject(picUri.toString(),Ph_numberString,Ph_exprString);

        mDatabase.child(USER_FIREBASE).child(auth.getCurrentUser().getUid()).child(DRIVER_FIREBASE).child(PRIVATE_HIRE_FIREBASE)
                .setValue(privateHireObject).addOnCompleteListener(new OnCompleteListener<Void>() {
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
