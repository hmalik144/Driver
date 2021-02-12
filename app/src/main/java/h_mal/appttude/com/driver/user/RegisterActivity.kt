package h_mal.appttude.com.driver.user

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import h_mal.appttude.com.driver.Global.FirebaseClass
import h_mal.appttude.com.driver.MainActivity
import h_mal.appttude.com.driver.Objects.UserObject
import h_mal.appttude.com.driver.R


class RegisterActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    private var name: EditText? = null
    private var email: EditText? = null
    private var passwordTop: EditText? = null
    private var passwordBottom: EditText? = null
    private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        name = findViewById(R.id.name_register)
        email = findViewById(R.id.email_register)
        passwordTop = findViewById(R.id.password_top)
        passwordBottom = findViewById(R.id.password_bottom)
        progressBar = findViewById(R.id.pb)
        val submit: Button = findViewById(R.id.email_sign_up)
        submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val nameString: String = name.getText().toString().trim({ it <= ' ' })
                val emailText: String = email.getText().toString().trim({ it <= ' ' })
                val passwordText: String = passwordTop.getText().toString().trim({ it <= ' ' })
                val passwordTextBottom: String =
                    passwordBottom.getText().toString().trim({ it <= ' ' })
                var cancel: Boolean = false
                var focusView: View? = null
                if (TextUtils.isEmpty(nameString)) {
                    name.setError(getString(R.string.error_field_required))
                    focusView = name
                    cancel = true
                }
                if (TextUtils.isEmpty(emailText)) {
                    email.setError(getString(R.string.error_field_required))
                    focusView = email
                    cancel = true
                }
                if (TextUtils.isEmpty(passwordText)) {
                    passwordTop.setError(getString(R.string.error_field_required))
                    focusView = passwordTop
                    cancel = true
                }
                if (TextUtils.isEmpty(passwordTextBottom)) {
                    passwordBottom.setError(getString(R.string.error_field_required))
                    focusView = passwordBottom
                    cancel = true
                }
                if (!TextUtils.isEmpty(passwordText) && !isPasswordValid(passwordText)) {
                    passwordTop.setError(getString(R.string.error_invalid_password))
                    focusView = passwordTop
                    cancel = true
                }
                if (!(passwordText == passwordTextBottom)) {
                    passwordBottom.setError(getString(R.string.no_match_password))
                    focusView = passwordBottom
                    cancel = true
                }
                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView!!.requestFocus()
                } else {
                    progressBar.setVisibility(View.VISIBLE)
                    //create user
                    auth.createUserWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener(
                            this@RegisterActivity,
                            object : OnCompleteListener<AuthResult?> {
                                override fun onComplete(task: Task<AuthResult?>) {
                                    progressBar.setVisibility(View.GONE)
                                    if (!task.isSuccessful) {
                                        Toast.makeText(
                                            this@RegisterActivity,
                                            "Authentication failed." + task.exception,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        val profileUpdatesBuilder: UserProfileChangeRequest.Builder =
                                            UserProfileChangeRequest.Builder()
                                        if (!TextUtils.isEmpty(nameString)) {
                                            profileUpdatesBuilder.setDisplayName(nameString)
                                        }
                                        val profileUpdates: UserProfileChangeRequest =
                                            profileUpdatesBuilder.build()
                                        auth.getCurrentUser()!!.updateProfile(profileUpdates)
                                            .addOnCompleteListener(object :
                                                OnCompleteListener<Void?> {
                                                override fun onComplete(task: Task<Void?>) {
                                                    if (task.isSuccessful) {
                                                        Log.i(
                                                            "RegisterActivity",
                                                            "onComplete: " + task.isSuccessful
                                                        )
                                                        val mDatabase: DatabaseReference =
                                                            FirebaseDatabase.getInstance()
                                                                .reference
                                                        mDatabase.child(FirebaseClass.USER_FIREBASE)
                                                            .child(
                                                                auth.getCurrentUser()!!.uid
                                                            ).child("role")
                                                            .setValue("driver")
                                                        mDatabase.child(FirebaseClass.USER_FIREBASE)
                                                            .child(
                                                                auth.getCurrentUser()!!.uid
                                                            ).child("user_details")
                                                            .setValue(
                                                                UserObject(
                                                                    auth.getCurrentUser()!!
                                                                        .displayName,
                                                                    auth.getCurrentUser()!!
                                                                        .email,
                                                                    null
                                                                )
                                                            )
                                                        val intent: Intent = Intent(
                                                            this@RegisterActivity,
                                                            MainActivity::class.java
                                                        )
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                                        startActivity(intent)
                                                        finish()
                                                    }
                                                }
                                            })
                                    }
                                }
                            })
                }
            }
        })
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 6
    }
}