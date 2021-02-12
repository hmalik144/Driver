package h_mal.appttude.com.driver.user

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import h_mal.appttude.com.driver.R


class forgotPasswordActivity : AppCompatActivity() {
    var TAG: String = "forgotPasswordActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        val editText: EditText = findViewById(R.id.reset_pw)
        val resetPw: Button = findViewById(R.id.reset_pw_sign_up)
        resetPw.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                resetPassword(editText.text.toString().trim({ it <= ' ' }))
            }
        })
    }

    private fun resetPassword(emailAddress: String) {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener(object : OnCompleteListener<Void?> {
                override fun onComplete(task: Task<Void?>) {
                    if (task.isSuccessful) {
                        Log.d(TAG, "Email sent.")
                        NavUtils.navigateUpFromSameTask(this@forgotPasswordActivity)
                        finish()
                    } else {
                        Toast.makeText(
                            this@forgotPasswordActivity,
                            "Could not reset Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }
}