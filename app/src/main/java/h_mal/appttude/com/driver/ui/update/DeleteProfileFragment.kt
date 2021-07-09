package h_mal.appttude.com.driver.update

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.utils.TextValidationUtils.validatePasswordEditText
import h_mal.appttude.com.driver.utils.setEnterPressedListener
import h_mal.appttude.com.driver.viewmodels.UpdateUserViewModel
import kotlinx.android.synthetic.main.fragment_update_email.*


class DeleteProfileFragment : BaseFragment<UpdateUserViewModel>() {

    private val viewmodel: UpdateUserViewModel by activityViewModels()
    override fun getViewModel(): UpdateUserViewModel = viewmodel
    override fun getLayoutId(): Int = R.layout.fragment_delete_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        password_top.setEnterPressedListener { deleteUser() }
        submission_button_label.setOnClickListener { deleteUser() }
    }

    private fun deleteUser(){
        val emailString = email_update.validatePasswordEditText() ?: return
        val passwordText = password_top.validatePasswordEditText() ?: return

        getViewModel().deleteProfile(emailString, passwordText)
    }

}