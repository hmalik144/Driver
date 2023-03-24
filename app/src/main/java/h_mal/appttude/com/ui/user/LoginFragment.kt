package h_mal.appttude.com.ui.user

import h_mal.appttude.com.R
import h_mal.appttude.com.base.BaseFragment
import h_mal.appttude.com.databinding.FragmentLoginBinding
import h_mal.appttude.com.utils.TextValidationUtils.validateEmailEditText
import h_mal.appttude.com.utils.TextValidationUtils.validatePasswordEditText
import h_mal.appttude.com.utils.navigateTo
import h_mal.appttude.com.utils.setEnterPressedListener
import h_mal.appttude.com.viewmodels.UserViewModel

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