package h_mal.appttude.com.driver;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        final EditText email = findViewById(R.id.email_register);
        final EditText passwordTop = findViewById(R.id.password_top);
        final EditText passwordBottom = findViewById(R.id.password_bottom);
        final ProgressBar progressBar = findViewById(R.id.pb);
        Button submit = findViewById(R.id.email_sign_up);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailText = email.getText().toString().trim();
                String passwordText = passwordTop.getText().toString().trim();
                String passwordTextBottom = passwordBottom.getText().toString().trim();

                if (TextUtils.isEmpty(emailText)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    email.setError(getString(R.string.error_field_required));
                    return;
                }

                if (TextUtils.isEmpty(passwordText)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    passwordTop.setError(getString(R.string.error_field_required));
                    return;
                }

                if (TextUtils.isEmpty(passwordTextBottom)) {
                    Toast.makeText(getApplicationContext(), "Enter password again!", Toast.LENGTH_SHORT).show();
                    passwordBottom.setError(getString(R.string.error_field_required));
                    return;
                }

                if (passwordText.length() < 6) {
                    passwordTop.setError(getString(R.string.error_invalid_password));
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    finish();
                                }
                            }
                        });

            }
        });
    }
}
