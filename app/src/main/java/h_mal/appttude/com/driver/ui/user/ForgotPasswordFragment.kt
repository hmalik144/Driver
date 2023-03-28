package h_mal.appttude.com.driver.ui.user

import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.databinding.FragmentForgotPasswordBinding
import h_mal.appttude.com.driver.utils.TextValidationUtils.validateEmailEditText
import h_mal.appttude.com.driver.viewmodels.UserViewModel


class ForgotPasswordFragment : BaseFragment<UserViewModel, FragmentForgotPasswordBinding>() {

    override fun setupView(binding: FragmentForgotPasswordBinding) = binding.run {
        submissionButton.setOnClickListener {
            val emailString = submissionEt.validateEmailEditText() ?: return@setOnClickListener
            viewModel.forgotPassword(emailString)
        }
    }

}