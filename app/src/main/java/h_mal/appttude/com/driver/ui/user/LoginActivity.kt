package h_mal.appttude.com.driver.ui.user


import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import h_mal.appttude.com.driver.ui.driver.MainActivity
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseActivity
import h_mal.appttude.com.driver.viewmodels.UserViewModel


/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : BaseActivity<UserViewModel>() {

    override fun getViewModel(): UserViewModel? = null
    override val layoutId: Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createViewModel<UserViewModel>()
    }

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