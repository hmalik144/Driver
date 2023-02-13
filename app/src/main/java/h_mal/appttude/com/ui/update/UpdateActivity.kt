package h_mal.appttude.com.ui.update

import android.os.Bundle
import h_mal.appttude.com.R
import h_mal.appttude.com.base.BaseActivity
import h_mal.appttude.com.data.FirebaseCompletion
import h_mal.appttude.com.utils.displayToast
import h_mal.appttude.com.viewmodels.UpdateUserViewModel

class UpdateActivity : BaseActivity<UpdateUserViewModel>() {

    override val layoutId: Int = R.layout.update_activity
    override fun getViewModel(): UpdateUserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createViewModel<UpdateUserViewModel>()
    }

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        when(data){
            is FirebaseCompletion.Changed -> displayToast(data.message)
        }
    }
}