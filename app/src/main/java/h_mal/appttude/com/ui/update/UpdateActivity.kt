package h_mal.appttude.com.ui.update

import h_mal.appttude.com.base.BaseActivity
import h_mal.appttude.com.data.FirebaseCompletion
import h_mal.appttude.com.databinding.UpdateActivityBinding
import h_mal.appttude.com.utils.displayToast
import h_mal.appttude.com.viewmodels.UpdateUserViewModel

class UpdateActivity : BaseActivity<UpdateUserViewModel, UpdateActivityBinding>() {

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        when (data) {
            is FirebaseCompletion.Changed -> displayToast(data.message)
        }
    }
}