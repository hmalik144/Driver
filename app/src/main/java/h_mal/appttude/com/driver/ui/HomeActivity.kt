package h_mal.appttude.com.driver.ui


import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.google.firebase.auth.FirebaseUser
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.DrawerActivity
import h_mal.appttude.com.driver.databinding.ActivityHomeBinding
import h_mal.appttude.com.driver.viewmodels.MainViewModel


class HomeActivity : DrawerActivity<MainViewModel,ActivityHomeBinding>() {

    override val containerId: Int = R.id.container
    override val drawerLayoutId: Int = R.id.drawer_layout
    override val toolbarId: Int = R.id.toolbar
    override val navViewId: Int = R.id.nav_view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupLogoutInDrawer()
        
        drawerLayout.addDrawerListener(object : DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) { }
            override fun onDrawerOpened(drawerView: View) {
                viewModel.getUserDetails()?.let { setupDrawer(it) }
            }
            override fun onDrawerClosed(drawerView: View) { }
            override fun onDrawerStateChanged(newState: Int) { }
        })
    }

    private fun setupLogoutInDrawer() {
        binding.logout.setOnClickListener {
            viewModel.logOut()
        }
    }
}