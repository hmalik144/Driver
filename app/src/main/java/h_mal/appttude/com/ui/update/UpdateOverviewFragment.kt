package h_mal.appttude.com.ui.update

import android.view.View
import h_mal.appttude.com.R
import h_mal.appttude.com.base.BaseFragment
import h_mal.appttude.com.databinding.UpdateOverviewFragmentBinding
import h_mal.appttude.com.utils.navigateTo
import h_mal.appttude.com.viewmodels.UpdateUserViewModel

class UpdateOverviewFragment : BaseFragment<UpdateUserViewModel, UpdateOverviewFragmentBinding>(),
    View.OnClickListener {

    override fun setupView(binding: UpdateOverviewFragmentBinding) = binding.run {
        updateEmailButton.setOnClickListener(this@UpdateOverviewFragment)
        updatePasswordButton.setOnClickListener(this@UpdateOverviewFragment)
        updateProfileButton.setOnClickListener(this@UpdateOverviewFragment)
        deleteProfile.setOnClickListener(this@UpdateOverviewFragment)
    }


    private fun View.submit() {
        when (id) {
            R.id.update_email_button -> navigateTo(R.id.to_updateEmailFragment)
            R.id.update_password_button -> navigateTo(R.id.to_updatePasswordFragment)
            R.id.update_profile_button -> navigateTo(R.id.to_updateProfileFragment)
            R.id.delete_profile -> navigateTo(R.id.to_deleteProfileFragment)
        }
    }

    override fun onClick(v: View?) {
        v?.submit()
    }

}