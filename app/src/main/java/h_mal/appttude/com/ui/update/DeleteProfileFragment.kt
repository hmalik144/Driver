package h_mal.appttude.com.ui.update

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import h_mal.appttude.com.R
import h_mal.appttude.com.base.BaseFragment
import h_mal.appttude.com.utils.TextValidationUtils.validatePasswordEditText
import h_mal.appttude.com.utils.setEnterPressedListener
import h_mal.appttude.com.viewmodels.UpdateUserViewModel
import kotlinx.android.synthetic.main.fragment_delete_profile.*


class DeleteProfileFragment : BaseFragment<UpdateUserViewModel>(R.layout.fragment_delete_profile) {

    private val viewmodel: UpdateUserViewModel by activityViewModels()
    override fun getViewModel(): UpdateUserViewModel = viewmodel

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