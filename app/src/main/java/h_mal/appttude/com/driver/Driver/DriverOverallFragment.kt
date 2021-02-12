package h_mal.appttude.com.driver.Driver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import h_mal.appttude.com.driver.Global.ExecuteFragment
import h_mal.appttude.com.driver.Global.FirebaseClass
import h_mal.appttude.com.driver.MainActivity
import h_mal.appttude.com.driver.Objects.ApprovalsObject
import h_mal.appttude.com.driver.Objects.WholeDriverObject
import h_mal.appttude.com.driver.R
import kotlinx.android.synthetic.main.fragment_driver_overall.*

class DriverOverallFragment : Fragment() {

    var reference: DatabaseReference? = null
    var driver_profObject: WholeDriverObject? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reference =
            MainActivity.mDatabase!!.child(FirebaseClass.USER_FIREBASE).child(
                MainActivity.auth!!.currentUser!!.uid
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_driver_overall, container, false)
        ph_button_exp.visibility = View.GONE
        dl_button_exp.visibility = View.GONE
        MainActivity.viewController!!.progress(View.VISIBLE)
        reference!!.addListenerForSingleValueEvent(valueEventListener)
        return view
    }

    var valueEventListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            driver_profObject = dataSnapshot.getValue(WholeDriverObject::class.java)
            if (driver_profObject!!.approvalsObject != null) {
                val approvalsObject: ApprovalsObject? = driver_profObject!!.approvalsObject
                approval_dp.setImageResource(
                    MainActivity.approvalsClass!!.setImageResource(
                        approvalsObject!!.driver_details_approval
                    )
                )
                approval_dp!!.setImageResource(
                    MainActivity.approvalsClass!!.setImageResource(
                        approvalsObject.private_hire_approval
                    )
                )
                approval_dl!!.setImageResource(
                    MainActivity.approvalsClass!!.setImageResource(
                        approvalsObject.driver_license_approval
                    )
                )
            }
            if (driver_profObject!!.driver_profile != null) {
                if (driver_profObject!!.driver_profile?.private_hire != null) {
                    ph_button_exp!!.visibility = View.VISIBLE
                    ph_button_exp!!.text = "Expiry: " + driver_profObject!!.driver_profile?.private_hire!!.phExpiry
                }
                if (driver_profObject!!.driver_profile!!.driver_license != null) {
                    dl_button_exp!!.visibility = View.VISIBLE
                    dl_button_exp!!.text = "Expiry: " + driver_profObject!!.driver_profile!!.driver_license!!.licenseExpiry
                }
            }
            driver_prof.setOnClickListener {
                ExecuteFragment.executeFragment(
                    DriverProfileFragment()
                )
            }
            private_hire.setOnClickListener {
                if (driver_profObject!!.driver_profile == null) {
                    ExecuteFragment.executeFragment(PrivateHireLicenseFragment())
                } else {
                    MainActivity.archiveClass!!.openDialogArchive(
                        context,
                        driver_profObject!!.driver_profile?.private_hire,
                        PrivateHireLicenseFragment()
                    )
                }
            }
            drivers_license!!.setOnClickListener {
                if (driver_profObject!!.driver_profile == null) {
                    ExecuteFragment.executeFragment(DriverLicenseFragment())
                } else {
                    MainActivity.archiveClass!!.openDialogArchive(
                        context,
                        driver_profObject!!.driver_profile?.driver_license,
                        DriverLicenseFragment()
                    )
                }
            }
            MainActivity.viewController!!.progress(View.GONE)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            MainActivity.viewController!!.progress(View.GONE)
        }
    }
}