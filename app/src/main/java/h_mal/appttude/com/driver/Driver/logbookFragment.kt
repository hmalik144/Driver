package h_mal.appttude.com.driver.Driver;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import h_mal.appttude.com.driver.Global.FirebaseClass;
import h_mal.appttude.com.driver.Global.ImageSelectorDialog;
import h_mal.appttude.com.driver.Global.ImageSelectorResults;
import h_mal.appttude.com.driver.Objects.LogbookObject;
import h_mal.appttude.com.driver.R;

import static h_mal.appttude.com.driver.Global.ExecuteFragment.UPLOAD_NEW;
import static h_mal.appttude.com.driver.Global.FirebaseClass.APPROVAL_CONSTANT;
import static h_mal.appttude.com.driver.Global.FirebaseClass.APPROVAL_PENDING;
import static h_mal.appttude.com.driver.Global.FirebaseClass.DRIVERS_LICENSE_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.INSURANCE_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.LOG_BOOK_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.USER_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.VEHICLE_FIREBASE;
import static h_mal.appttude.com.driver.Global.ImageSelectorDialog.CAMERA_REQUEST;
import static h_mal.appttude.com.driver.Global.ImageSelectorDialog.MY_CAMERA_PERMISSION_CODE;
import static h_mal.appttude.com.driver.MainActivity.approvalsClass;
import static h_mal.appttude.com.driver.MainActivity.archiveClass;
import static h_mal.appttude.com.driver.MainActivity.auth;
import static h_mal.appttude.com.driver.MainActivity.fragmentManager;
import static h_mal.appttude.com.driver.MainActivity.getDateStamp;
import static h_mal.appttude.com.driver.MainActivity.loadImage;
import static h_mal.appttude.com.driver.MainActivity.mDatabase;
import static h_mal.appttude.com.driver.MainActivity.viewController;


public class logbookFragment extends Fragment {

    private String TAG = this.getClass().getSimpleName();
    DatabaseReference reference;

    TextView uploadlb;
    ImageView lbImage;
    ProgressBar progressBar;
    EditText v5cNumber;

    public Uri filePath;
    public Uri picUri;

    String v5cString;

    LogbookObject logbookObject;
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

        reference = mDatabase.child(USER_FIREBASE).child(UID).child(VEHICLE_FIREBASE).child(LOG_BOOK_FIREBASE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logbook, container, false);

        uploadlb = view.findViewById(R.id.upload_lb);
        lbImage = view.findViewById(R.id.log_book_img);
        progressBar = view.findViewById(R.id.pb_lb);
        v5cNumber = view.findViewById(R.id.v5c_no);
        Button submit = view.findViewById(R.id.submit_lb);

        viewController.progress(View.VISIBLE);
        reference.addListenerForSingleValueEvent(valueEventListener);

        uploadlb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelectorDialog imageSelectorDialog = new ImageSelectorDialog(getContext());
                imageSelectorDialog.setImageName("logbook_pic"+getDateStamp());
                imageSelectorDialog.show();
            }
        });


        submit.setOnClickListener(submitOnClickListener);

        return view;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            viewController.progress(View.GONE);
            try{
                logbookObject = dataSnapshot.getValue(LogbookObject.class);
            }catch (Exception e){
                Log.e(TAG, "onDataChange: ", e);
            }finally {
                if (logbookObject != null){
                    picUri = Uri.parse(logbookObject.getPhotoString());
                    v5cString = logbookObject.getV5cnumber();

                    if(!uploadNew) {
                        v5cNumber.setText(v5cString);
                        Picasso.get()
                                .load(picUri)
                                .into(loadImage(progressBar, lbImage));
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
            v5cString = v5cNumber.getText().toString().trim();

            if (!TextUtils.isEmpty(v5cString)){
                viewController.progress(View.VISIBLE);
                if (filePath == null && picUri == null){
                    Toast.makeText(getContext(), "No Driver image", Toast.LENGTH_SHORT).show();
                    viewController.progress(View.GONE);
                }else {
                    if (filePath != null){
                        Log.i(TAG, "onClick: new Image uploaded");
                        new FirebaseClass(getContext(),filePath,new FirebaseClass.Response(){
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
                        }).uploadImage(LOG_BOOK_FIREBASE,LOG_BOOK_FIREBASE + getDateStamp());
                    }else{
                        Log.i(TAG, "onClick: pushing with same image");
                        publishObject();
                    }
                }


            }else {
                if (TextUtils.isEmpty(v5cString)){
                    v5cNumber.setError("Field required");
                }
                if (picUri == null){
                    Toast.makeText(getContext(), getString(R.string.image_required), Toast.LENGTH_SHORT).show();
                }
            }

        }

    };

    private void publishObject(){

        if (uploadNew){
            archiveClass.archiveRecord(UID,LOG_BOOK_FIREBASE,logbookObject);
        }

        LogbookObject logbookObjectNew = new LogbookObject(picUri.toString(), v5cString);

        reference.setValue(logbookObjectNew).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    approvalsClass.setStatusCode(UID,LOG_BOOK_FIREBASE + APPROVAL_CONSTANT,APPROVAL_PENDING);
                    fragmentManager.popBackStack();
                }else{
                    Toast.makeText(getContext(), "Upload Unsuccessful", Toast.LENGTH_SHORT).show();
                }
                viewController.progress(View.VISIBLE);
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
                filePath,lbImage,new ImageSelectorResults.FilepathResponse() {
                    @Override
                    public void processFinish(Uri output) {
                        filePath = output;
                    }
                });

    }
}
