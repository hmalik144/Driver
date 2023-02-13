package h_mal.appttude.com.ui.update

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import h_mal.appttude.com.R
import h_mal.appttude.com.base.BaseFragment
import h_mal.appttude.com.utils.navigateTo
import h_mal.appttude.com.viewmodels.UpdateUserViewModel
import kotlinx.android.synthetic.main.update_overview_fragment.*

class UpdateOverviewFragment : BaseFragment<UpdateUserViewModel>(R.layout.update_overview_fragment), View.OnClickListener {

    private val vm by activityViewModels<UpdateUserViewModel>()
    override fun getViewModel(): UpdateUserViewModel = vm

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        update_email_button.setOnClickListener(this)
        update_password_button.setOnClickListener(this)
        update_profile_button.setOnClickListener(this)
        delete_profile.setOnClickListener(this)
    }

    private fun View.submit(){
        when(id){
            R.id.update_email_button -> navigateTo(R.id.to_updateEmailFragment)
            R.id.update_password_button -> navigateTo(R.id.to_updatePasswordFragment)
            R.id.update_profile_button -> navigateTo(R.id.to_updateProfileFragment)
            R.id.delete_profile -> navigateTo(R.id.to_deleteProfileFragment)
        }
    }

    override fun onClick(v: View?){
        v?.submit()
    }


}