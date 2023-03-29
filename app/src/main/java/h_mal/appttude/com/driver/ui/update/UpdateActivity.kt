package h_mal.appttude.com.driver.ui.update

import h_mal.appttude.com.driver.base.BaseActivity
import h_mal.appttude.com.driver.data.FirebaseCompletion
import h_mal.appttude.com.driver.databinding.UpdateActivityBinding
import h_mal.appttude.com.driver.utils.displayToast
import h_mal.appttude.com.driver.viewmodels.UpdateUserViewModel

class UpdateActivity : BaseActivity<UpdateUserViewModel, UpdateActivityBinding>() {

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        when (data) {
            is FirebaseCompletion.Changed -> displayToast(data.message)
        }
    }
}