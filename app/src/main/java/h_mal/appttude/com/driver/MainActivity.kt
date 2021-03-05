package h_mal.appttude.com.driver

import android.Manifest
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import h_mal.appttude.com.driver.Global.ApprovalsClass
import h_mal.appttude.com.driver.Global.ArchiveClass
import h_mal.appttude.com.driver.Global.ImageViewClass
import h_mal.appttude.com.driver.base.BaseActivity
import h_mal.appttude.com.driver.dialogs.ExitDialog.displayExitDialog
import h_mal.appttude.com.driver.user.LoginActivity
import h_mal.appttude.com.driver.utils.ActivityIntentBuilder.createIntent
import h_mal.appttude.com.driver.utils.setPicassoImage
import h_mal.appttude.com.driver.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : BaseActivity<MainViewModel>(),
    NavigationView.OnNavigationItemSelectedListener {

    private val vm by createLazyViewModel<MainViewModel>()
    override fun getViewModel(): MainViewModel = vm
    override val layoutId: Int = R.layout.activity_main

    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageViewClass = ImageViewClass()
        approvalsClass = ApprovalsClass()
        archiveClass = ArchiveClass()

        setSupportActionBar(toolbar)
        navController = findNavController(R.id.container)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)
        nav_view.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        getViewModel().getUserDetails()
        setupLogoutInDrawer()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun setTitle(title: CharSequence) {
        toolbar.title = title
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.container)
            navHostFragment?.childFragmentManager?.backStackEntryCount?.takeIf { it > 1 }?.let {
                return super.onBackPressed()
            }
            displayExitDialog()
        }
    }

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        when(data){
            is FirebaseUser -> {
                setupDrawer(data)
            }
        }
    }

    private fun setupDrawer(user: FirebaseUser) {
        val header: View = nav_view.getHeaderView(0)
        header.driver_email.text = user.email
        header.driver_name.text = user.displayName
        header.profileImage.setPicassoImage(user.photoUrl)
    }

    private fun setupLogoutInDrawer(){
        logout.setOnClickListener {
            getViewModel().logOut()
            startActivity(createIntent<LoginActivity>())
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.itemId == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_user_settings -> { }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    companion object {

        lateinit var imageViewClass: ImageViewClass
        lateinit var approvalsClass: ApprovalsClass
        lateinit var archiveClass: ArchiveClass
        private const val REQUEST_EXTERNAL_STORAGE: Int = 1
        private val PERMISSIONS_STORAGE: Array<String> = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        fun loadImage(mainImage: ImageView?): Target {
            val target: Target = object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
                    mainImage!!.setImageBitmap(bitmap)
                    mainImage.setOnClickListener{ imageViewClass.open(bitmap) }
                }

                override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {}
                override fun onPrepareLoad(placeHolderDrawable: Drawable) {
                    mainImage!!.setImageResource(R.drawable.choice_img)
                }
            }
            mainImage!!.tag = target
            return target
        }

    }
}