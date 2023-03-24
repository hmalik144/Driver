package h_mal.appttude.com.ui.user

import h_mal.appttude.com.base.BaseFragment
import h_mal.appttude.com.databinding.FragmentForgotPasswordBinding
import h_mal.appttude.com.utils.TextValidationUtils.validateEmailEditText
import h_mal.appttude.com.viewmodels.UserViewModel


class ForgotPasswordFragment : BaseFragment<UserViewModel, FragmentForgotPasswordBinding>() {

    override fun setupView(binding: FragmentForgotPasswordBinding) = binding.run {
        submissionButton.setOnClickListener {
            val emailString = submissionEt.validateEmailEditText() ?: return@setOnClickListener
            viewModel.forgotPassword(emailString)
        }
    }

}