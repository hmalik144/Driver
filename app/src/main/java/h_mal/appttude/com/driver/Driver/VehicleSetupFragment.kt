package h_mal.appttude.com.driver.Driver

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import h_mal.appttude.com.driver.Global.DateDialog
import h_mal.appttude.com.driver.Global.ExecuteFragment
import h_mal.appttude.com.driver.Global.FirebaseClass
import h_mal.appttude.com.driver.MainActivity
import h_mal.appttude.com.driver.Objects.VehicleProfileObject
import h_mal.appttude.com.driver.R


class VehicleSetupFragment : Fragment() {
    private val TAG: String = this.javaClass.simpleName
    var reg: EditText? = null
    var make: EditText? = null
    var model: EditText? = null
    var color: EditText? = null
    var keeperName: EditText? = null
    var address: EditText? = null
    var postcode: EditText? = null
    var startDate: EditText? = null
    var seized: CheckBox? = null
    var Submit: Button? = null
    var ref: DatabaseReference? = null
    var vehicleProfileObject: VehicleProfileObject? = null
    var uploadNew: Boolean? = null
    var UID: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uploadNew = false
        if (arguments != null) {
            Log.i(TAG, "onCreate: args = args exist")
            if (arguments!!.containsKey("user_id")) {
                UID = arguments!!.getString("user_id")
            } else {
                UID = MainActivity.auth!!.currentUser!!.uid
            }
            if (arguments!!.containsKey(ExecuteFragment.UPLOAD_NEW)) {
                uploadNew = true
            }
        } else {
            UID = MainActivity.auth!!.currentUser!!.uid
        }
        ref = MainActivity.mDatabase!!.child(FirebaseClass.USER_FIREBASE).child(
            (UID)!!
        ).child(FirebaseClass.VEHICLE_FIREBASE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_vehicle_setup, container, false)
        reg = view.findViewById(R.id.reg)
        make = view.findViewById(R.id.make)
        model = view.findViewById(R.id.model)
        color = view.findViewById(R.id.colour)
        keeperName = view.findViewById(R.id.keeper_name)
        address = view.findViewById(R.id.address)
        postcode = view.findViewById(R.id.postcode)
        startDate = view.findViewById(R.id.start_date)
        seized = view.findViewById(R.id.seized)
        Submit = view.findViewById(R.id.submit_vehicle)
        startDate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val dateDialog: DateDialog = DateDialog((context)!!)
                dateDialog.init(startDate)
            }
        })
        MainActivity.viewController!!.progress(View.VISIBLE)
        ref!!.addListenerForSingleValueEvent(valueEventListener)
        Submit.setOnClickListener(submitOnClickListener)
        return view
    }

    var valueEventListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            MainActivity.viewController!!.progress(View.GONE)
            try {
                vehicleProfileObject =
                    dataSnapshot.child(FirebaseClass.VEHICLE_DETAILS_FIREBASE).getValue(
                        VehicleProfileObject::class.java
                    )
            } catch (e: Exception) {
                Log.e(TAG, "onDataChange: ", e)
            } finally {
                if (vehicleProfileObject != null) {
                    if (!uploadNew!!) {
                        reg.setText(vehicleProfileObject.getReg())
                        make.setText(vehicleProfileObject.getMake())
                        model.setText(vehicleProfileObject.getModel())
                        color.setText(vehicleProfileObject.getColour())
                        keeperName.setText(vehicleProfileObject.getKeeperName())
                        address.setText(vehicleProfileObject.getKeeperAddress())
                        postcode.setText(vehicleProfileObject.getKeeperPostCode())
                        startDate.setText(vehicleProfileObject.getStartDate())
                        seized!!.isChecked = vehicleProfileObject!!.isSeized()
                    }
                }
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            MainActivity.viewController!!.progress(View.GONE)
        }
    }
    var submitOnClickListener: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View) {
            val regString: String = reg!!.text.toString().trim({ it <= ' ' })
            val makeString: String = make!!.text.toString().trim({ it <= ' ' })
            val modelString: String = model!!.text.toString().trim({ it <= ' ' })
            val colourString: String = color!!.text.toString().trim({ it <= ' ' })
            val keeperNameStrin: String = keeperName!!.text.toString().trim({ it <= ' ' })
            val addressString: String = address!!.text.toString().trim({ it <= ' ' })
            val postcodeString: String = postcode!!.text.toString().trim({ it <= ' ' })
            val driverForename: String = startDate!!.text.toString().trim({ it <= ' ' })
            if ((!TextUtils.isEmpty(regString)
                        && !TextUtils.isEmpty(makeString)
                        && !TextUtils.isEmpty(modelString)
                        && !TextUtils.isEmpty(colourString)
                        && !TextUtils.isEmpty(keeperNameStrin)
                        && !TextUtils.isEmpty(addressString)
                        && !TextUtils.isEmpty(postcodeString)
                        && !TextUtils.isEmpty(driverForename))
            ) {
                if ((uploadNew)!!) {
                    MainActivity.archiveClass!!.archiveRecord(
                        UID,
                        FirebaseClass.VEHICLE_DETAILS_FIREBASE,
                        vehicleProfileObject
                    )
                }
                val vehicleProfileObject: VehicleProfileObject = VehicleProfileObject(
                    regString,
                    makeString,
                    modelString,
                    colourString,
                    keeperNameStrin,
                    addressString,
                    postcodeString,
                    driverForename,
                    seized!!.isChecked
                )
                MainActivity.viewController!!.progress(View.VISIBLE)
                ref!!.child(FirebaseClass.VEHICLE_DETAILS_FIREBASE)
                    .setValue(vehicleProfileObject)
                    .addOnCompleteListener(object : OnCompleteListener<Void?> {
                        override fun onComplete(task: Task<Void?>) {
                            if (task.isSuccessful) {
                                MainActivity.approvalsClass!!.setStatusCode(
                                    UID,
                                    FirebaseClass.VEHICLE_DETAILS_FIREBASE + FirebaseClass.APPROVAL_CONSTANT,
                                    FirebaseClass.APPROVAL_PENDING
                                )
                                MainActivity.approvalsClass!!.setStatusCode(
                                    UID,
                                    FirebaseClass.MOT_FIREBASE + FirebaseClass.APPROVAL_CONSTANT,
                                    FirebaseClass.APPROVAL_PENDING
                                )
                                MainActivity.approvalsClass!!.setStatusCode(
                                    UID,
                                    FirebaseClass.INSURANCE_FIREBASE + FirebaseClass.APPROVAL_CONSTANT,
                                    FirebaseClass.APPROVAL_PENDING
                                )
                                MainActivity.approvalsClass!!.setStatusCode(
                                    UID,
                                    FirebaseClass.LOG_BOOK_FIREBASE + FirebaseClass.APPROVAL_CONSTANT,
                                    FirebaseClass.APPROVAL_PENDING
                                )
                                MainActivity.fragmentManager!!.popBackStack()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Upload Unsuccessful",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            MainActivity.viewController!!.progress(View.GONE)
                        }
                    })
            } else {
                if (TextUtils.isEmpty(regString)) {
                    reg!!.error = "Field required"
                }
                if (TextUtils.isEmpty(makeString)) {
                    make!!.error = "Field required"
                }
                if (TextUtils.isEmpty(modelString)) {
                    model!!.error = "Field required"
                }
                if (TextUtils.isEmpty(colourString)) {
                    color!!.error = "Field required"
                }
                if (TextUtils.isEmpty(keeperNameStrin)) {
                    keeperName!!.error = "Field required"
                }
                if (TextUtils.isEmpty(addressString)) {
                    address!!.error = "Field required"
                }
                if (TextUtils.isEmpty(postcodeString)) {
                    postcode!!.error = "Field required"
                }
                if (TextUtils.isEmpty(driverForename)) {
                    startDate!!.error = "Field required"
                }
            }
        }
    }
}