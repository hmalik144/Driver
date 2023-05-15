package h_mal.appttude.com.driver.ui.update

import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.databinding.FragmentUpdatePasswordBinding
import h_mal.appttude.com.driver.utils.TextValidationUtils.validateEmailEditText
import h_mal.appttude.com.driver.utils.TextValidationUtils.validatePasswordEditText
import h_mal.appttude.com.driver.utils.setEnterPressedListener
import h_mal.appttude.com.driver.viewmodels.UpdateUserViewModel


class UpdatePasswordFragment : BaseFragment<UpdateUserViewModel, FragmentUpdatePasswordBinding>() {

    override fun setupView(binding: FragmentUpdatePasswordBinding) {
        applyBinding {
            emailUpdate.setEnterPressedListener { registerUser() }
            submit.setOnClickListener { registerUser() }
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