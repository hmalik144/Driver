package h_mal.appttude.com.ui.update

import h_mal.appttude.com.base.BaseFragment
import h_mal.appttude.com.databinding.FragmentUpdateEmailBinding
import h_mal.appttude.com.utils.TextValidationUtils.validateEmailEditText
import h_mal.appttude.com.utils.TextValidationUtils.validatePasswordEditText
import h_mal.appttude.com.utils.setEnterPressedListener
import h_mal.appttude.com.viewmodels.UpdateUserViewModel


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