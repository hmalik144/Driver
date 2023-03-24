package h_mal.appttude.com.ui.user


import android.content.Intent
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import h_mal.appttude.com.base.BaseActivity
import h_mal.appttude.com.databinding.ActivityLoginBinding
import h_mal.appttude.com.ui.MainActivity
import h_mal.appttude.com.viewmodels.UserViewModel


/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : BaseActivity<UserViewModel, ActivityLoginBinding>() {

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        if (data is AuthResult || data is FirebaseUser) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

}