package h_mal.appttude.com.driver.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseUser
import h_mal.appttude.com.driver.databinding.NavHeaderMainBinding
import h_mal.appttude.com.driver.dialogs.ExitDialog.displayExitDialog
import h_mal.appttude.com.driver.utils.isTrue
import h_mal.appttude.com.driver.utils.setGlideImage

abstract class DrawerActivity<V : BaseViewModel, VB : ViewBinding> : BaseActivity<V, VB>(),
    NavigationView.OnNavigationItemSelectedListener {

    abstract val containerId: Int
    abstract val drawerLayoutId: Int
    abstract val toolbarId: Int
    abstract val navViewId: Int

    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar = findViewById(toolbarId)
        drawerLayout = findViewById(drawerLayoutId)
        navView = findViewById(navViewId)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        navController = findNavController(containerId)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun setTitle(title: CharSequence) {
        toolbar.title = title
    }

    override fun onBackPressed() {
        applyBinding {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                navController.backQueue.size.let { it >= 1 }.isTrue {
                    super.onBackPressed()
                    return@applyBinding
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}