package h_mal.appttude.com.driver.update

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.utils.TextValidationUtils.validateEmailEditText
import h_mal.appttude.com.driver.utils.TextValidationUtils.validatePasswordEditText
import h_mal.appttude.com.driver.utils.setEnterPressedListener
import h_mal.appttude.com.driver.viewmodels.UpdateUserViewModel
import kotlinx.android.synthetic.main.fragment_update_email.*


class UpdateEmailFragment : BaseFragment<UpdateUserViewModel>() {

    private val viewmodel: UpdateUserViewModel by activityViewModels()
    override fun getViewModel(): UpdateUserViewModel = viewmodel
    override fun getLayoutId(): Int = R.layout.fragment_update_email

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        new_email.setEnterPressedListener { registerUser() }
        submission_button_label.setOnClickListener { registerUser() }
    }

    private fun registerUser(){
        val emailString = email_update.validatePasswordEditText() ?: return
        val passwordText = password_top.validatePasswordEditText() ?: return
        val newEmail = new_email.validateEmailEditText() ?: return

        getViewModel().updateEmail(emailString, passwordText, newEmail)
    }


}