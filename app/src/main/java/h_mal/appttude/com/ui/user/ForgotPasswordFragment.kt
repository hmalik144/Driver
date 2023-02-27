package h_mal.appttude.com.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import h_mal.appttude.com.R
import h_mal.appttude.com.base.BaseFragment
import h_mal.appttude.com.utils.TextValidationUtils.validateEmailEditText
import h_mal.appttude.com.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_forgot_password.*


class ForgotPasswordFragment : BaseFragment<UserViewModel>(R.layout.fragment_forgot_password) {

    private val userViewModel: UserViewModel by activityViewModels()
    override fun getViewModel(): UserViewModel = userViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submission_button.setOnClickListener {
            val emailString = submission_et.validateEmailEditText() ?: return@setOnClickListener
            userViewModel.forgotPassword(emailString)
        }
    }

}