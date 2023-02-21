package h_mal.appttude.com.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import h_mal.appttude.com.R
import h_mal.appttude.com.base.BaseFragment
import h_mal.appttude.com.utils.TextValidationUtils.validateEmailEditText
import h_mal.appttude.com.utils.TextValidationUtils.validatePasswordEditText
import h_mal.appttude.com.utils.navigateTo
import h_mal.appttude.com.utils.setEnterPressedListener
import h_mal.appttude.com.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<UserViewModel>(R.layout.fragment_login) {

    private val userViewModel: UserViewModel by activityViewModels()
    override fun getViewModel(): UserViewModel = userViewModel

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