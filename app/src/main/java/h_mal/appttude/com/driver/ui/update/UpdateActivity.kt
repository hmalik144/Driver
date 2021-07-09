package h_mal.appttude.com.driver.update

import android.os.Bundle
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseActivity
import h_mal.appttude.com.driver.viewmodels.UpdateUserViewModel

class UpdateActivity : BaseActivity<UpdateUserViewModel>() {

    override val layoutId: Int = R.layout.update_activity
    override fun getViewModel(): UpdateUserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createViewModel<UpdateUserViewModel>()
    }
}