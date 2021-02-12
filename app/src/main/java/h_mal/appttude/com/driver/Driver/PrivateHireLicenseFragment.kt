package h_mal.appttude.com.driver.Driver;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import h_mal.appttude.com.driver.Global.DateDialog;
import h_mal.appttude.com.driver.Global.FirebaseClass;
import h_mal.appttude.com.driver.Global.ImageSelectorDialog;
import h_mal.appttude.com.driver.Global.ImageSelectorResults;
import h_mal.appttude.com.driver.Objects.PrivateHireObject;
import h_mal.appttude.com.driver.R;

import static h_mal.appttude.com.driver.Global.ExecuteFragment.UPLOAD_NEW;
import static h_mal.appttude.com.driver.Global.FirebaseClass.*;
import static h_mal.appttude.com.driver.Global.ImageSelectorDialog.CAMERA_REQUEST;
import static h_mal.appttude.com.driver.Global.ImageSelectorDialog.MY_CAMERA_PERMISSION_CODE;
import static h_mal.appttude.com.driver.MainActivity.approvalsClass;
import static h_mal.appttude.com.driver.MainActivity.archiveClass;
import static h_mal.appttude.com.driver.MainActivity.fragmentManager;
import static h_mal.appttude.com.driver.MainActivity.getDateStamp;
import static h_mal.appttude.com.driver.MainActivity.loadImage;
import static h_mal.appttude.com.driver.MainActivity.mDatabase;
import static h_mal.appttude.com.driver.MainActivity.auth;
import static h_mal.appttude.com.driver.MainActivity.viewController;

public class PrivateHireLicenseFragment extends Fragment {

    private String TAG = this.getClass().getSimpleName();

    private ImageView imageView;
    ProgressBar pb;

    EditText phNo;
    EditText phExpiry;

    public Uri filePath;

    public Uri picUri;
    String Ph_numberString;
    String Ph_exprString;

    DatabaseReference reference;

    PrivateHireObject privateHireObject;
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

        reference = mDatabase.child(USER_FIREBASE).child(UID)
                .child(DRIVER_FIREBASE).child(PRIVATE_HIRE_FIREBASE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_private_hire_license, container, false);

        viewController.progress(View.VISIBLE);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                viewController.progress(View.GONE);

                try{
                    privateHireObject = dataSnapshot.getValue(PrivateHireObject.class);
                }catch (Exception e){
                    Log.e(TAG, "onDataChange: ", e);
                }finally {
                    if (privateHireObject != null){
                        picUri = Uri.parse(privateHireObject.getPhImageString());
                        Ph_numberString = privateHireObject.getPhNumber();
                        Ph_exprString = privateHireObject.getPhExpiry();

                        Log.i(TAG, "onDataChange: uploadNew = " + uploadNew);
                        if (!uploadNew){
                            phNo.setText(Ph_numberString);
                            phExpiry.setText(Ph_exprString);
                            Picasso.get()
                                    .load(picUri)
                                    .into(loadImage(pb,imageView));
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                viewController.progress(View.GONE);
            }
        });

        TextView uploadPH = view.findViewById(R.id.uploadphlic);
        imageView = view.findViewById(R.id.imageView2);
        pb = view.findViewById(R.id.pb_priv);

        phNo = view.findViewById(R.id.ph_no);
        phExpiry  = view.findViewById(R.id.ph_expiry);

        phExpiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dateDialog = new DateDialog(getContext());
                dateDialog.init(phExpiry);
                dateDialog.show();
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
                    viewController.progress(View.VISIBLE);
                    if (filePath == null && picUri == null){
                        Toast.makeText(getContext(), "No Driver image", Toast.LENGTH_SHORT).show();
                        viewController.progress(View.GONE);
                    }else {
                        if (filePath != null){
                            Log.i(TAG, "onClick: new Image uploaded");
                            new FirebaseClass(getContext(),filePath,new Response(){
                                @Override
                                public void processFinish(Uri output) {
                                    Log.i(TAG, "processFinish: ");
                                    if (output != null){
                                        picUri = output;
                                        publishObject();

                                    }else {
                                        Toast.makeText(getContext(), R.string.unsuccessful, Toast.LENGTH_SHORT).show();
                                        viewController.progress(View.GONE);
                                    }

                                }
                            }).uploadImage(PRIVATE_HIRE_FIREBASE,PRIVATE_HIRE_FIREBASE + getDateStamp());
                        }else{
                            Log.i(TAG, "onClick: pushing with same image");
                            publishObject();
                        }
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

        if (uploadNew){
            archiveClass.archiveRecord(UID,PRIVATE_HIRE_FIREBASE,privateHireObject);
        }

        PrivateHireObject privateHireObjectNew = new PrivateHireObject(picUri.toString(),Ph_numberString,Ph_exprString);

        mDatabase.child(USER_FIREBASE).child(UID).child(DRIVER_FIREBASE).child(PRIVATE_HIRE_FIREBASE)
                .setValue(privateHireObjectNew).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.i(TAG, "onComplete: publish = " + task.isSuccessful());
                    approvalsClass.setStatusCode(UID,PRIVATE_HIRE_FIREBASE + APPROVAL_CONSTANT,APPROVAL_PENDING);
                    fragmentManager.popBackStack();
                }else {
                    Toast.makeText(getContext(), R.string.unsuccessful, Toast.LENGTH_SHORT).show();
                }
                viewController.progress(View.GONE);

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

        new ImageSelectorResults().Results(getActivity(),requestCode, resultCode, data,
                filePath,imageView,new ImageSelectorResults.FilepathResponse() {
                    @Override
                    public void processFinish(Uri output) {
                        filePath = output;
                    }
                });

    }

}
