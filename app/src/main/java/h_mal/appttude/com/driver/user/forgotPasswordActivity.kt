package h_mal.appttude.com.driver.User;

import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import h_mal.appttude.com.driver.R;

public class forgotPasswordActivity extends AppCompatActivity {

    String TAG = "forgotPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        final EditText editText = findViewById(R.id.reset_pw);

        final Button resetPw = findViewById(R.id.reset_pw_sign_up);
        resetPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword(editText.getText().toString().trim());
            }
        });

    }


    private void resetPassword(String emailAddress){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");

                            NavUtils.navigateUpFromSameTask(forgotPasswordActivity.this);
                            finish();
                        }else {
                            Toast.makeText(forgotPasswordActivity.this, "Could not reset Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
