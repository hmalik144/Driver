package h_mal.appttude.com.ui.update

import h_mal.appttude.com.base.BaseFragment
import h_mal.appttude.com.databinding.FragmentDeleteProfileBinding
import h_mal.appttude.com.utils.TextValidationUtils.validatePasswordEditText
import h_mal.appttude.com.utils.setEnterPressedListener
import h_mal.appttude.com.viewmodels.UpdateUserViewModel


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