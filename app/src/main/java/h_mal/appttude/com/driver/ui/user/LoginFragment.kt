package h_mal.appttude.com.driver.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.utils.TextValidationUtils.validateEmailEditText
import h_mal.appttude.com.driver.utils.TextValidationUtils.validatePasswordEditText
import h_mal.appttude.com.driver.utils.navigateTo
import h_mal.appttude.com.driver.utils.setEnterPressedListener
import h_mal.appttude.com.driver.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<UserViewModel>() {

    private val userViewModel: UserViewModel by activityViewModels()
    override fun getViewModel(): UserViewModel = userViewModel
    override fun getLayoutId(): Int = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        password.setEnterPressedListener { attemptLogin() }

        email_sign_in_button.setOnClickListener { attemptLogin() }
        register_button.setOnClickListener { it.navigateTo(R.id.to_register) }
        forgot.setOnClickListener { it.navigateTo(R.id.to_forgotPassword) }
    }

    private fun attemptLogin(){
        // Store values at the time of the login attempt.
        val emailString = email.validateEmailEditText() ?: return
        val passwordString = password.validatePasswordEditText() ?: return

        // attempt to login user
        getViewModel().signInUser(emailString, passwordString)
    }

}