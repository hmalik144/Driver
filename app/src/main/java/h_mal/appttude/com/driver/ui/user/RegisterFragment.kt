package h_mal.appttude.com.driver.ui.user

import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.databinding.FragmentRegisterBinding
import h_mal.appttude.com.driver.utils.TextValidationUtils.validateEmailEditText
import h_mal.appttude.com.driver.utils.TextValidationUtils.validatePasswordEditText
import h_mal.appttude.com.driver.utils.setEnterPressedListener
import h_mal.appttude.com.driver.viewmodels.UserViewModel

class RegisterFragment :
    BaseFragment<UserViewModel, FragmentRegisterBinding>() {

    override fun setupView(binding: FragmentRegisterBinding) = binding.run {
        passwordBottom.setEnterPressedListener { registerUser() }
        emailSignUp.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        applyBinding {
            val nameString = nameRegister.validatePasswordEditText() ?: return@applyBinding
            val emailText = emailRegister.validateEmailEditText() ?: return@applyBinding
            val passwordText = passwordTop.validatePasswordEditText() ?: return@applyBinding
            val passwordTextBottom =
                passwordBottom.validatePasswordEditText() ?: return@applyBinding

            if ((passwordText != passwordTextBottom)) {
                passwordBottom.error = getString(R.string.no_match_password)
                passwordBottom.requestFocus()
                return@applyBinding
            }

            viewModel.registerUser(nameString, emailText, passwordText)
        }

    }
}