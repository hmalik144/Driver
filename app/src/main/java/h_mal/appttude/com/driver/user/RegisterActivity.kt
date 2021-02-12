package h_mal.appttude.com.driver.User;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import h_mal.appttude.com.driver.MainActivity;
import h_mal.appttude.com.driver.Objects.UserObject;
import h_mal.appttude.com.driver.R;

import static h_mal.appttude.com.driver.Global.FirebaseClass.USER_FIREBASE;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText name;
    private EditText email;
    private EditText passwordTop;
    private EditText passwordBottom;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        name = findViewById(R.id.name_register);
        email = findViewById(R.id.email_register);
        passwordTop = findViewById(R.id.password_top);
        passwordBottom = findViewById(R.id.password_bottom);
        progressBar = findViewById(R.id.pb);
        Button submit = findViewById(R.id.email_sign_up);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nameString = name.getText().toString().trim();
                String emailText = email.getText().toString().trim();
                String passwordText = passwordTop.getText().toString().trim();
                String passwordTextBottom = passwordBottom.getText().toString().trim();

                boolean cancel = false;
                View focusView = null;

                if (TextUtils.isEmpty(nameString)) {
                    name.setError(getString(R.string.error_field_required));
                    focusView = name;
                    cancel = true;
                }

                if (TextUtils.isEmpty(emailText)) {
                    email.setError(getString(R.string.error_field_required));
                    focusView = email;
                    cancel = true;
                }

                if (TextUtils.isEmpty(passwordText)) {
                    passwordTop.setError(getString(R.string.error_field_required));
                    focusView = passwordTop;
                    cancel = true;
                }

                if (TextUtils.isEmpty(passwordTextBottom)) {
                    passwordBottom.setError(getString(R.string.error_field_required));
                    focusView = passwordBottom;
                    cancel = true;
                }

                if (!TextUtils.isEmpty(passwordText) && !isPasswordValid(passwordText)) {
                    passwordTop.setError(getString(R.string.error_invalid_password));
                    focusView = passwordTop;
                    cancel = true;
                }

                if (!passwordText.equals(passwordTextBottom)){
                    passwordBottom.setError(getString(R.string.no_match_password));
                    focusView = passwordBottom;
                    cancel = true;
                }

                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    //create user
                    auth.createUserWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    UserProfileChangeRequest.Builder profileUpdatesBuilder = new UserProfileChangeRequest.Builder();

                                    if (!TextUtils.isEmpty(nameString)){
                                        profileUpdatesBuilder.setDisplayName(nameString);
                                    }

                                    UserProfileChangeRequest profileUpdates = profileUpdatesBuilder.build();

                                    auth.getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Log.i("RegisterActivity", "onComplete: " + task.isSuccessful());

                                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                                                mDatabase.child(USER_FIREBASE).child(auth.getCurrentUser().getUid()).child("role")
                                                        .setValue("driver");
                                                mDatabase.child(USER_FIREBASE).child(auth.getCurrentUser().getUid()).child("user_details")
                                                        .setValue(new UserObject(auth.getCurrentUser().getDisplayName(),auth.getCurrentUser().getEmail(),null));

                                                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                finish();
                                            }

                                        }
                                    });


                                }
                            }
                        });
                    }

            }
        });
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 6;
    }
}
