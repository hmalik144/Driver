package h_mal.appttude.com.driver.ui.update

import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.databinding.FragmentDeleteProfileBinding
import h_mal.appttude.com.driver.utils.TextValidationUtils.validatePasswordEditText
import h_mal.appttude.com.driver.utils.setEnterPressedListener
import h_mal.appttude.com.driver.viewmodels.UpdateUserViewModel


class DeleteProfileFragment :
    BaseFragment<UpdateUserViewModel, FragmentDeleteProfileBinding>() {

    override fun setupView(binding: FragmentDeleteProfileBinding) = binding.run {
        passwordTop.setEnterPressedListener { deleteUser() }
        submissionButtonLabel.setOnClickListener { deleteUser() }
    }

    private fun deleteUser() = applyBinding {
        val emailString = emailUpdate.validatePasswordEditText() ?: return@applyBinding
        val passwordText = passwordTop.validatePasswordEditText() ?: return@applyBinding

        viewModel.deleteProfile(emailString, passwordText)
    }

}