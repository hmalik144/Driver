package h_mal.appttude.com.ui


import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseUser
import h_mal.appttude.com.R
import h_mal.appttude.com.base.BaseActivity
import h_mal.appttude.com.databinding.ActivityMainBinding
import h_mal.appttude.com.databinding.NavHeaderMainBinding
import h_mal.appttude.com.dialogs.ExitDialog.displayExitDialog
import h_mal.appttude.com.utils.isTrue
import h_mal.appttude.com.utils.setGlideImage
import h_mal.appttude.com.viewmodels.MainViewModel


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    NavigationView.OnNavigationItemSelectedListener {

    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun setupView(binding: ActivityMainBinding) = binding.run {
        setSupportActionBar(appBarLayout.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        navController = findNavController(R.id.container)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        viewModel.getUserDetails()
        setupLogoutInDrawer()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun setTitle(title: CharSequence) {
        applyBinding {
            appBarLayout.toolbar.title = title
        }

    }

    override fun onBackPressed() {
        applyBinding {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.container)
                navHostFragment?.childFragmentManager?.backStackEntryCount?.let { it >= 1 }?.isTrue {
                    super.onBackPressed()
                }
                displayExitDialog()
            }
        }
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
        applyBinding {
            NavHeaderMainBinding.inflate(layoutInflater).apply {
                driverEmail.text = user.email
                driverName.text = user.displayName
                profileImage.setGlideImage(user.photoUrl)
            }
        }
    }

    private fun setupLogoutInDrawer() {
        applyBinding {
            logout.setOnClickListener {
                viewModel.logOut()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_user_settings -> {}
        }
        applyBinding {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        return true
    }
}