package h_mal.appttude.com.driver.ui.update

import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.databinding.FragmentUpdateEmailBinding
import h_mal.appttude.com.driver.utils.TextValidationUtils.validateEmailEditText
import h_mal.appttude.com.driver.utils.TextValidationUtils.validatePasswordEditText
import h_mal.appttude.com.driver.utils.setEnterPressedListener
import h_mal.appttude.com.driver.viewmodels.UpdateUserViewModel


class UpdateEmailFragment : BaseFragment<UpdateUserViewModel, FragmentUpdateEmailBinding>() {

    override fun setupView(binding: FragmentUpdateEmailBinding) = binding.run {
        newEmail.setEnterPressedListener { registerUser() }
        submissionButtonLabel.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        applyBinding {
            val emailString = emailUpdate.validatePasswordEditText() ?: return@applyBinding
            val passwordText = passwordTop.validatePasswordEditText() ?: return@applyBinding
            val newEmail = newEmail.validateEmailEditText() ?: return@applyBinding

            viewModel.updateEmail(emailString, passwordText, newEmail)
        }
    }

}