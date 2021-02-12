package h_mal.appttude.com.driver.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import h_mal.appttude.com.driver.Global.ImageSelectorDialog;
import h_mal.appttude.com.driver.Global.ImageSelectorResults;
import h_mal.appttude.com.driver.Global.ViewController;
import h_mal.appttude.com.driver.Objects.UserObject;
import h_mal.appttude.com.driver.R;

import static h_mal.appttude.com.driver.Global.FirebaseClass.DRIVER_DETAILS_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.DRIVER_FIREBASE;
import static h_mal.appttude.com.driver.Global.FirebaseClass.USER_FIREBASE;
import static h_mal.appttude.com.driver.Global.ImageSelectorDialog.CAMERA_REQUEST;
import static h_mal.appttude.com.driver.Global.ImageSelectorDialog.MY_CAMERA_PERMISSION_CODE;
import static h_mal.appttude.com.driver.MainActivity.auth;
import static h_mal.appttude.com.driver.MainActivity.fragmentManager;
import static h_mal.appttude.com.driver.MainActivity.mDatabase;

public class profileFragment extends Fragment {

    private String TAG = this.getClass().getSimpleName();

    private TextView email;
    private TextView name;
    private TextView changePw;

    private FirebaseUser user;
    private DatabaseReference databaseReference;

    private static final String EMAIL_CONSTANT = "Email Address";
    private static final String PW_CONSTANT = "Password";

    ViewController viewController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewController = new ViewController(getActivity());

        user = auth.getCurrentUser();
        databaseReference = mDatabase.child(USER_FIREBASE).child(user.getUid())
                .child(DRIVER_FIREBASE).child(DRIVER_DETAILS_FIREBASE)
                .child("driverPic");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        email = view.findViewById(R.id.change_email);
        name = view.findViewById(R.id.change_profile_name);
        changePw = view.findViewById(R.id.change_pw);

        Button button = view.findViewById(R.id.submit_profile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Update Username");

                final EditText titleBox = new EditText(getContext());
                titleBox.setText(user.getDisplayName());
                dialog.setView(titleBox);
                dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateProfile(titleBox.getText().toString().trim());
                    }
                });
                dialog.show();

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(EMAIL_CONSTANT);
            }
        });

        changePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(PW_CONSTANT);
            }
        });

        return view;
    }

    private void updateProfile(String profileName){
        UserProfileChangeRequest.Builder profileUpdatesBuilder = new UserProfileChangeRequest.Builder();

        if (!TextUtils.isEmpty(profileName)){
            profileUpdatesBuilder.setDisplayName(profileName);
        }

        UserProfileChangeRequest profileUpdates = profileUpdatesBuilder.build();

        user.updateProfile(profileUpdates)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User profile updated.");
                        viewController.reloadDrawer();
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                }
        });


    }

    private void changeCredentials(String email, String password, final String changeText, final String selector){
        // Get auth credentials from the user for re-authentication
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password); // Current Login Credentials \\
        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");

                        user = FirebaseAuth.getInstance().getCurrentUser();
                        if (selector.equals(EMAIL_CONSTANT)){
                            user.updateEmail(changeText)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User email address updated.");
                                                Toast.makeText(getContext(), "Update Successful", Toast.LENGTH_SHORT).show();
                                                viewController.reloadDrawer();
                                            }
                                        }
                                    });
                        }
                        if (selector.equals(PW_CONSTANT)){
                            user.updatePassword(changeText)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User email address updated.");
                                                Toast.makeText(getContext(), "Update Successful", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }



                    }
                });
    }

    private void showDialog(final String update){
        //Make new Dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Update " + update);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(28,0,56,0);

        final EditText box1 = new EditText(getContext());
        box1.setHint("Current Email Address");
        box1.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        layout.addView(box1); // Notice this is an add method

        final EditText box2 = new EditText(getContext());
        box2.setHint("Current Password");
        box2.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(box2); // Another add method

        final EditText box3 = new EditText(getContext());
        if (update.equals(PW_CONSTANT)){
            box3.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else {
            box3.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }
        box3.setHint("New " + update);
        layout.addView(box3); // Another add method

        dialog.setView(layout);
        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = box1.getText().toString().trim();
                String password = box2.getText().toString().trim();
                String textThree = box3.getText().toString().trim();

                changeCredentials(email,password,textThree,update);
            }
        });
        dialog.show();
    }
}
