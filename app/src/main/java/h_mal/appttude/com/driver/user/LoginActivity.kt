package h_mal.appttude.com.driver.user

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.utils.Navigations
import h_mal.appttude.com.driver.utils.displayToast
import h_mal.appttude.com.driver.utils.navigateToActivity

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null

    // UI references.
    private var mEmailView: EditText? = null
    private var mPasswordView: EditText? = null
    private var mProgressView: View? = null
    private var mLoginView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        // Set up the login form.
        mEmailView = findViewById(R.id.email)
        mPasswordView = findViewById<View>(R.id.password) as EditText
        mPasswordView!!.setOnEditorActionListener(OnEditorActionListener { textView, id, keyEvent ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })
        val mEmailSignInButton = findViewById<Button>(R.id.email_sign_in_button)
        mEmailSignInButton.setOnClickListener { view: View? -> attemptLogin() }
        mProgressView = findViewById(R.id.login_progress)
        mLoginView = findViewById(R.id.email_login_form)
        val register = findViewById<TextView>(R.id.register_button)
        register.setOnClickListener { v: View? ->
            val intent = Intent(application, RegisterActivity::class.java)
            startActivity(intent)
        }
        val forgotPw = findViewById<TextView>(R.id.forgot)
        forgotPw.setOnClickListener { v: View? ->
            val intent = Intent(application, forgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
        if (auth == null) {
            displayToast("Login invalid")
            return
        }

        // Reset errors
        mEmailView!!.error = null
        mPasswordView!!.error = null

        // Store values at the time of the login attempt.
        val email = mEmailView!!.text.toString()
        val password = mPasswordView!!.text.toString()
        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView!!.error = getString(R.string.error_invalid_password)
            focusView = mPasswordView
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView!!.error = getString(R.string.error_field_required)
            focusView = mEmailView
            cancel = true
        } else if (!isEmailValid(email)) {
            mEmailView!!.error = getString(R.string.error_invalid_email)
            focusView = mEmailView
            cancel = true
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView!!.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true)
            auth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task: Task<AuthResult?> ->
                    showProgress(false)
                    if (task.isSuccessful) {
                        navigateToActivity(
                            this@LoginActivity,
                            Navigations.Main
                        )
                        finish()
                    } else {
                        displayToast(getString(R.string.login_failed))
                    }
                }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 6
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)
        mLoginView!!.visibility = if (show) View.GONE else View.VISIBLE
        mLoginView!!.animate().setDuration(shortAnimTime.toLong()).alpha(
            if (show) 0F else 1.toFloat()
        ).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mLoginView!!.visibility = if (show) View.GONE else View.VISIBLE
            }
        })
        mProgressView!!.visibility = if (show) View.VISIBLE else View.GONE
        mProgressView!!.animate().setDuration(shortAnimTime.toLong()).alpha(
            if (show) 1F else 0.toFloat()
        ).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mProgressView!!.visibility = if (show) View.VISIBLE else View.GONE
            }
        })
    }
}