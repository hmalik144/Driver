package h_mal.appttude.com.ui.update

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import h_mal.appttude.com.R
import h_mal.appttude.com.base.BaseFragment
import h_mal.appttude.com.utils.TextValidationUtils.validateEmailEditText
import h_mal.appttude.com.utils.TextValidationUtils.validatePasswordEditText
import h_mal.appttude.com.utils.setEnterPressedListener
import h_mal.appttude.com.viewmodels.UpdateUserViewModel
import kotlinx.android.synthetic.main.fragment_update_password.*


class UpdatePasswordFragment :
    BaseFragment<UpdateUserViewModel>(R.layout.fragment_update_password) {

    private val viewmodel: UpdateUserViewModel by activityViewModels()
    override fun getViewModel(): UpdateUserViewModel = viewmodel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email_update.setEnterPressedListener { registerUser() }
        email_sign_up.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        val emailString = email_update.validatePasswordEditText() ?: return
        val passwordText = password_top.validatePasswordEditText() ?: return
        val newPassword = password_bottom.validateEmailEditText() ?: return

        getViewModel().updatePassword(emailString, passwordText, newPassword)
    }

}