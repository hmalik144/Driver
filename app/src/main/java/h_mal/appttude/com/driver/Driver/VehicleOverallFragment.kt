package h_mal.appttude.com.driver.Driver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
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


class VehicleOverallFragment : Fragment() {
    var vehicleApr: ImageView? = null
    var insuranceApr: ImageView? = null
    var motApr: ImageView? = null
    var logbookApr: ImageView? = null
    private var privateHireCarApr: ImageView? = null
    var insuranceExp: TextView? = null
    var motExp: TextView? = null
    private var privateHireExp: TextView? = null
    private var vehicleProfile: CardView? = null
    private var insurance: CardView? = null
    private var mot: CardView? = null
    private var logbook: CardView? = null
    private var privateHireCar: CardView? = null
    private var reference: DatabaseReference? = null
    private var wholeDriverObject: WholeDriverObject? = null
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
        val view: View = inflater.inflate(R.layout.fragment_vehicle_overall, container, false)
        vehicleProfile = view.findViewById(R.id.vehicle_prof)
        insurance = view.findViewById(R.id.insurance)
        mot = view.findViewById(R.id.mot)
        logbook = view.findViewById(R.id.logbook)
        privateHireCar = view.findViewById(R.id.private_hire_vehicle_license)
        vehicleApr = view.findViewById(R.id.approval_vehicle)
        insuranceApr = view.findViewById(R.id.approval_insurance)
        motApr = view.findViewById(R.id.approval_mot)
        logbookApr = view.findViewById(R.id.approval_lb)
        privateHireCarApr = view.findViewById(R.id.approval_ph_car)
        insuranceExp = view.findViewById(R.id.ins_exp)
        motExp = view.findViewById(R.id.mot_exp)
        privateHireExp = view.findViewById(R.id.ph_car_exp)
        insuranceExp.setVisibility(View.GONE)
        motExp.setVisibility(View.GONE)
        privateHireExp.setVisibility(View.GONE)
        MainActivity.viewController!!.progress(View.VISIBLE)
        reference!!.addListenerForSingleValueEvent(valueEventListener)
        return view
    }

    var valueEventListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            wholeDriverObject = dataSnapshot.getValue(WholeDriverObject::class.java)
            if (wholeDriverObject!!.approvalsObject != null) {
                val approvalsObject: ApprovalsObject? = wholeDriverObject.approvalsObject
                vehicleApr!!.setImageResource(
                    MainActivity.approvalsClass!!.setImageResource(
                        approvalsObject.vehicle_details_approval
                    )
                )
                insuranceApr!!.setImageResource(
                    MainActivity.approvalsClass!!.setImageResource(
                        approvalsObject.insurance_details_approval
                    )
                )
                motApr!!.setImageResource(
                    MainActivity.approvalsClass!!.setImageResource(
                        approvalsObject.getMot_details_approval()
                    )
                )
                logbookApr!!.setImageResource(
                    MainActivity.approvalsClass!!.setImageResource(
                        approvalsObject.getLog_book_approval()
                    )
                )
                privateHireCarApr!!.setImageResource(
                    MainActivity.approvalsClass!!.setImageResource(
                        approvalsObject.getPh_car_approval()
                    )
                )
            }
            if (wholeDriverObject!!.vehicle_profile != null) {
                if (wholeDriverObject.getVehicle_profile().insurance_details != null) {
                    insuranceExp!!.visibility = View.VISIBLE
                    insuranceExp!!.text = "Expiry: " + wholeDriverObject.getVehicle_profile().getInsurance_details()
                        .getExpiryDate()
                }
                if (wholeDriverObject.getVehicle_profile().mot_details != null) {
                    motExp!!.visibility = View.VISIBLE
                    motExp!!.text = "Expiry: " + wholeDriverObject.getVehicle_profile().getMot_details()
                        .getMotExpiry()
                }
                if (wholeDriverObject.getVehicle_profile().private_hire_vehicle != null) {
                    privateHireExp!!.visibility = View.VISIBLE
                    privateHireExp!!.text = "Expiry: " + wholeDriverObject.getVehicle_profile()
                        .getPrivateHireVehicleObject().getPhCarExpiry()
                }
            }
            vehicleProfile!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    if (wholeDriverObject!!.vehicle_profile == null) {
                        ExecuteFragment.executeFragment(VehicleSetupFragment())
                    } else {
                        MainActivity.archiveClass!!.openDialogArchive(
                            context,
                            wholeDriverObject.getVehicle_profile().getVehicle_details(),
                            VehicleSetupFragment()
                        )
                    }
                }
            })
            insurance!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    if (wholeDriverObject!!.vehicle_profile == null) {
                        ExecuteFragment.executeFragment(InsuranceFragment())
                    } else {
                        MainActivity.archiveClass!!.openDialogArchive(
                            context,
                            wholeDriverObject.getVehicle_profile().getInsurance_details(),
                            InsuranceFragment()
                        )
                    }
                }
            })
            mot!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    if (wholeDriverObject!!.vehicle_profile == null) {
                        ExecuteFragment.executeFragment(MotFragment())
                    } else {
                        MainActivity.archiveClass!!.openDialogArchive(
                            context,
                            wholeDriverObject.getVehicle_profile().getMot_details(),
                            MotFragment()
                        )
                    }
                }
            })
            logbook!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    if (wholeDriverObject!!.vehicle_profile == null) {
                        ExecuteFragment.executeFragment(logbookFragment())
                    } else {
                        MainActivity.archiveClass!!.openDialogArchive(
                            context,
                            wholeDriverObject.getVehicle_profile().getLog_book(),
                            logbookFragment()
                        )
                    }
                }
            })
            privateHireCar!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    if (wholeDriverObject!!.vehicle_profile == null) {
                        ExecuteFragment.executeFragment(PrivateHireVehicleFragment())
                    } else {
                        MainActivity.archiveClass!!.openDialogArchive(
                            context,
                            wholeDriverObject.getVehicle_profile().getPrivateHireVehicleObject(),
                            PrivateHireVehicleFragment()
                        )
                    }
                }
            })
            MainActivity.viewController!!.progress(View.GONE)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            MainActivity.viewController!!.progress(View.GONE)
        }
    }
}