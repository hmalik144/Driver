package h_mal.appttude.com.driver.ui.update

import h_mal.appttude.com.driver.base.BaseActivity
import h_mal.appttude.com.driver.data.FirebaseCompletion.Changed
import h_mal.appttude.com.driver.data.FirebaseCompletion.ProfileDeleted
import h_mal.appttude.com.driver.databinding.UpdateActivityBinding
import h_mal.appttude.com.driver.viewmodels.UpdateUserViewModel

class UpdateActivity : BaseActivity<UpdateUserViewModel, UpdateActivityBinding>() {

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        when (data) {
            is Changed -> showSnackBar(data.message)
            is ProfileDeleted -> showToast(data.message)
        }
    }
}