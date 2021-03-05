package h_mal.appttude.com.driver.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.utils.TextValidationUtils.validateEmailEditText
import h_mal.appttude.com.driver.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_forgot_password.*


class ForgotPasswordFragment : BaseFragment<UserViewModel>() {

    private val userViewModel: UserViewModel by activityViewModels()

    override fun getViewModel(): UserViewModel = userViewModel
    override fun getLayoutId(): Int = R.layout.fragment_forgot_password

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submission_button.setOnClickListener {
            val emailString = submission_et.validateEmailEditText() ?: return@setOnClickListener
            userViewModel.forgotPassword(emailString)
        }
    }

}