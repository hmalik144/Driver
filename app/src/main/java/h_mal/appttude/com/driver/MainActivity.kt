package h_mal.appttude.com.driver

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import h_mal.appttude.com.driver.Driver.DriverOverallFragment
import h_mal.appttude.com.driver.Driver.VehicleOverallFragment
import h_mal.appttude.com.driver.Driver.homeDriverFragment
import h_mal.appttude.com.driver.Global.*
import h_mal.appttude.com.driver.Global.ViewController.ViewControllerInterface
import h_mal.appttude.com.driver.SuperUser.homeSuperUserFragment
import h_mal.appttude.com.driver.user.LoginActivity
import h_mal.appttude.com.driver.user.profileFragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener, ViewControllerInterface {
    var navigationView: NavigationView? = null
    var progressBar: ProgressBar? = null
    var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewController = ViewController(this)
        imageViewClass = ImageViewClass()
        approvalsClass = ApprovalsClass()
        archiveClass = ArchiveClass()
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
        mDatabase = FirebaseDatabase.getInstance().reference
        val ref: DatabaseReference = mDatabase!!.child(FirebaseClass.USER_FIREBASE)
            .child(auth.getCurrentUser()!!.uid)
            .child("role")
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        val drawer: DrawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView = findViewById<View>(R.id.nav_view) as NavigationView?
        navigationView!!.setNavigationItemSelectedListener(this)
        setupDrawer()
        fragmentManager = supportFragmentManager
        fragmentManager.addOnBackStackChangedListener(backStackChangedListener)
        progressBar = findViewById(R.id.progress_bar)
        progressBar.setVisibility(View.VISIBLE)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val role: String? = dataSnapshot.value as String?
                Log.i(TAG, "onDataChange: " + role)
                if ((role == "driver")) {
                    ExecuteFragment.executeFragment(homeDriverFragment())
                } else if ((role == "super_user")) {
                    ExecuteFragment.executeFragment(homeSuperUserFragment())
                }
                drawerMenuItems(role)
                progressBar.setVisibility(View.GONE)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                progressBar.setVisibility(View.GONE)
            }
        })
    }

    var backStackChangedListener: FragmentManager.OnBackStackChangedListener =
        object : FragmentManager.OnBackStackChangedListener {
            override fun onBackStackChanged() {
                val fragmentString: String =
                    fragmentManager!!.fragments.get(0).javaClass.simpleName
                val title: String
                when (fragmentString) {
                    "DriverProfileFragment" -> title = "Driver Profile"
                    "DriverLicenseFragment" -> title = "Drivers License"
                    "InsuranceFragment" -> title = "Insurance"
                    "logbookFragment" -> title = "Logbook"
                    "MotFragment" -> title = "M.O.T"
                    "PrivateHireLicenseFragment" -> title = "Private Hire License"
                    "VehicleSetupFragment" -> title = "Vehicle Profile"
                    "UserMainFragment" -> return
                    "ArchiveFragment" -> return
                    else -> title = resources.getString(R.string.app_name)
                }
                setTitle(title)
            }
        }

    override fun setTitle(title: CharSequence) {
        toolbar!!.title = title
    }

    fun drawerMenuItems(s: String?) {
        if ((s == "super_user")) {
            val menu: Menu = navigationView!!.menu
            menu.removeGroup(R.id.menu_group)
        }
    }

    fun setupDrawer() {
        val header: View = navigationView!!.getHeaderView(0)
        val driverEmail: TextView = header.findViewById(R.id.driver_email)
        val driverName: TextView = header.findViewById(R.id.driver_name)
        val driverImage: ImageView = header.findViewById(R.id.profileImage)
        if (auth != null) {
            val user: FirebaseUser? = auth!!.currentUser
            if (user!!.email != null) {
                driverEmail.text = user.email
            }
            if (user.displayName != null) {
                driverName.text = user.displayName
            }
            Picasso.get()
                .load(user.photoUrl)
                .placeholder(R.drawable.choice_img_round)
                .into(driverImage)
        }
        val textView: TextView = findViewById(R.id.logout)
        textView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                auth!!.signOut()
                val intent: Intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    override fun onBackPressed() {
        val drawer: DrawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (fragmentManager!!.backStackEntryCount > 1) {
                if ((fragmentManager!!.fragments.get(0).javaClass
                        .simpleName == "InsuranceFragment")
                ) {
                    AlertDialog.Builder(this)
                        .setTitle("Return to previous?")
                        .setMessage("Progress unsaved. Are you sure?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(
                            android.R.string.yes,
                            object : DialogInterface.OnClickListener {
                                override fun onClick(arg0: DialogInterface, arg1: Int) {
                                    fragmentManager!!.popBackStack()
                                }
                            }).create().show()
                } else {
                    fragmentManager!!.popBackStack()
                }
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Leave?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(
                        android.R.string.yes,
                        object : DialogInterface.OnClickListener {
                            override fun onClick(arg0: DialogInterface, arg1: Int) {
                                finish()
                                System.exit(0)
                            }
                        }).create().show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.itemId
        if (id == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id: Int = item.itemId
        if (id == R.id.nav_camera) {
            // Handle the camera action
            ExecuteFragment.executeFragment(profileFragment())
        } else if (id == R.id.nav_gallery) {
            ExecuteFragment.executeFragment(DriverOverallFragment())
        } else if (id == R.id.nav_slideshow) {
            ExecuteFragment.executeFragment(VehicleOverallFragment())
        }
        val drawer: DrawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun progressVisibility(vis: Int) {
        progressBar!!.visibility = vis
    }

    override fun updateDrawer() {
        setupDrawer()
    }

    companion object {
        private val TAG: String = MainActivity::class.java.simpleName
        var fragmentManager: FragmentManager? = null
        var auth: FirebaseAuth? = null
        var storage: FirebaseStorage? = null
        var storageReference: StorageReference? = null
        var mDatabase: DatabaseReference? = null
        var viewController: ViewController? = null
        var imageViewClass: ImageViewClass? = null
        var approvalsClass: ApprovalsClass? = null
        var archiveClass: ArchiveClass? = null
        private val REQUEST_EXTERNAL_STORAGE: Int = 1
        private val PERMISSIONS_STORAGE: Array<String> = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        fun verifyStoragePermissions(activity: Activity?) {
            // Check if we have write permission
            val permission: Int = ActivityCompat.checkSelfPermission(
                (activity)!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                    (activity),
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
                )
            }
        }

        fun loadImage(mainImage: ImageView?): Target {
            val target: Target = object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
                    mainImage!!.setImageBitmap(bitmap)
                    mainImage.setOnClickListener(View.OnClickListener { imageViewClass!!.open(bitmap) })
                }

                override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {}
                override fun onPrepareLoad(placeHolderDrawable: Drawable) {
                    mainImage!!.setImageResource(R.drawable.choice_img)
                }
            }
            mainImage!!.tag = target
            return target
        }

        val dateStamp: String
            get() {
                val sdf: SimpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmm")
                return sdf.format(Date())
            }
        val dateTimeStamp: String
            get() {
                val sdf: SimpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss")
                return sdf.format(Date())
            }

        @Throws(ParseException::class)
        fun setAsDateTime(strCurrentDate: String?): String {
            var format: SimpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss")
            val newDate: Date = format.parse(strCurrentDate)
            format = SimpleDateFormat("dd/MM/yyyy")
            return format.format(newDate)
        }

        fun printObjectAsJson(TAG: String?, o: Any?) {
            val gson: Gson = GsonBuilder().setPrettyPrinting().create()
            val jp: JsonParser = JsonParser()
            val je: JsonElement = jp.parse(Gson().toJson(o))
            val prettyJsonString: String = gson.toJson(je)
            Log.i(TAG, "onBindViewHolder: object" + prettyJsonString)
        }
    }
}