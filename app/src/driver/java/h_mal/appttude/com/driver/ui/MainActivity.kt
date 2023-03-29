package h_mal.appttude.com.driver.ui


import android.os.Bundle
import com.google.firebase.auth.FirebaseUser
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.DrawerActivity
import h_mal.appttude.com.driver.databinding.ActivityMainBinding
import h_mal.appttude.com.driver.databinding.NavHeaderMainBinding
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.MainViewModel


class MainActivity : DrawerActivity<MainViewModel, ActivityMainBinding>() {

    override val containerId: Int = R.id.container
    override val drawerLayoutId: Int = R.id.drawer_layout
    override val toolbarId: Int = R.id.toolbar
    override val navViewId: Int = R.id.nav_view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getUserDetails()
        setupLogoutInDrawer()
    }

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)

        when (data) {
            is FirebaseUser -> {
                setupDrawer(data)
            }
        }
    }

    private fun setupDrawer(user: FirebaseUser) {
        NavHeaderMainBinding.inflate(layoutInflater).apply {
            driverEmail.text = user.email
            driverName.text = user.displayName
            profileImage.setGlideImage(user.photoUrl)
        }
    }

    private fun setupLogoutInDrawer() {
        binding.logout.setOnClickListener {
            viewModel.logOut()
        }
    }
}