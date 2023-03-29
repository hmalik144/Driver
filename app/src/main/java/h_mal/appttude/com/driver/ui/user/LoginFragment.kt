package h_mal.appttude.com.driver.ui.user

import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.databinding.FragmentLoginBinding
import h_mal.appttude.com.driver.utils.TextValidationUtils.validateEmailEditText
import h_mal.appttude.com.driver.utils.TextValidationUtils.validatePasswordEditText
import h_mal.appttude.com.driver.utils.navigateTo
import h_mal.appttude.com.driver.utils.setEnterPressedListener
import h_mal.appttude.com.driver.viewmodels.UserViewModel

class LoginFragment : BaseFragment<UserViewModel, FragmentLoginBinding>() {

    override fun setupView(binding: FragmentLoginBinding) = binding.run {
        password.setEnterPressedListener { attemptLogin() }
        emailSignInButton.setOnClickListener { attemptLogin() }
        registerButton.setOnClickListener { it.navigateTo(R.id.to_register) }
        forgot.setOnClickListener { it.navigateTo(R.id.to_forgotPassword) }
    }

    private fun attemptLogin() {
        applyBinding {
            // Store values at the time of the login attempt.
            val emailString = email.validateEmailEditText() ?: return@applyBinding
            val passwordString = password.validatePasswordEditText() ?: return@applyBinding

            // attempt to login user
            viewModel.signInUser(emailString, passwordString)
        }
    }

}