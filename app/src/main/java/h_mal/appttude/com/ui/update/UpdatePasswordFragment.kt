package h_mal.appttude.com.ui.update

import h_mal.appttude.com.base.BaseFragment
import h_mal.appttude.com.databinding.FragmentUpdatePasswordBinding
import h_mal.appttude.com.utils.TextValidationUtils.validateEmailEditText
import h_mal.appttude.com.utils.TextValidationUtils.validatePasswordEditText
import h_mal.appttude.com.utils.setEnterPressedListener
import h_mal.appttude.com.viewmodels.UpdateUserViewModel


class UpdatePasswordFragment : BaseFragment<UpdateUserViewModel, FragmentUpdatePasswordBinding>() {

    override fun setupView(binding: FragmentUpdatePasswordBinding) {
        applyBinding {
            emailUpdate.setEnterPressedListener { registerUser() }
            emailSignUp.setOnClickListener { registerUser() }
        }
    }

    private fun registerUser() {
        applyBinding {
            val emailString = emailUpdate.validatePasswordEditText() ?: return@applyBinding
            val passwordText = passwordTop.validatePasswordEditText() ?: return@applyBinding
            val newPassword = passwordBottom.validateEmailEditText() ?: return@applyBinding

            viewModel.updatePassword(emailString, passwordText, newPassword)
        }
    }

}