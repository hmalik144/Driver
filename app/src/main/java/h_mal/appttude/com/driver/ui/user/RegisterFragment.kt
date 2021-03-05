package h_mal.appttude.com.driver.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.utils.TextValidationUtils.validateEmailEditText
import h_mal.appttude.com.driver.utils.TextValidationUtils.validatePasswordEditText
import h_mal.appttude.com.driver.utils.setEnterPressedListener
import h_mal.appttude.com.driver.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseFragment<UserViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_register
    override fun getViewModel(): UserViewModel {
        val userViewModel: UserViewModel by activityViewModels()
        return userViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        password_bottom.setEnterPressedListener { registerUser() }
        email_sign_up.setOnClickListener { registerUser() }
    }

    private fun registerUser(){
        val nameString = name_register.validatePasswordEditText() ?: return
        val emailText = email_register.validateEmailEditText() ?: return
        val passwordText = password_top.validatePasswordEditText() ?: return
        val passwordTextBottom = password_bottom.validatePasswordEditText() ?: return

        if ((passwordText != passwordTextBottom)) {
            password_bottom.error = getString(R.string.no_match_password)
            password_bottom.requestFocus()
            return
        }

        getViewModel().registerUser(nameString, emailText, passwordText)
    }
}