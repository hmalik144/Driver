package h_mal.appttude.com.driver.Driver

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import h_mal.appttude.com.driver.Global.DateDialog
import h_mal.appttude.com.driver.Global.FirebaseClass
import h_mal.appttude.com.driver.Global.ImageSelectorDialog
import h_mal.appttude.com.driver.Global.ImageSelectorResults
import h_mal.appttude.com.driver.Global.ImageSelectorResults.FilepathResponse
import h_mal.appttude.com.driver.MainActivity
import h_mal.appttude.com.driver.Objects.DriverProfileObject
import h_mal.appttude.com.driver.Objects.UserObject
import h_mal.appttude.com.driver.R


class DriverProfileFragment : Fragment() {
    private val TAG: String = this.javaClass.simpleName
    var driverPic: ImageView? = null
    var addPic: TextView? = null
    var forenames: EditText? = null
    var address: EditText? = null
    var postcode: EditText? = null
    var dob: EditText? = null
    var ni: EditText? = null
    var dateFirst: EditText? = null
    var submit_driver: Button? = null
    var filePath: Uri? = null
    var picUri: Uri? = null
    var driverProfileReference: DatabaseReference? = null
    var UID: String? = null
    var driverProfileObject: DriverProfileObject? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            UID = arguments!!.getString("user_id")
        } else {
            UID = MainActivity.auth!!.currentUser!!.uid
        }
        driverProfileReference =
            MainActivity.mDatabase!!.child(FirebaseClass.USER_FIREBASE).child(
                (UID)!!
            )
                .child(FirebaseClass.DRIVER_FIREBASE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_driver_profile, container, false)
        driverPic = view.findViewById(R.id.driver_pic)
        addPic = view.findViewById(R.id.add_driver_pic)
        forenames = view.findViewById(R.id.names)
        address = view.findViewById(R.id.address)
        postcode = view.findViewById(R.id.postcode)
        dob = view.findViewById(R.id.dob)
        ni = view.findViewById(R.id.ni_number)
        dateFirst = view.findViewById(R.id.date_first)
        submit_driver = view.findViewById(R.id.submit_driver)
        MainActivity.viewController!!.progress(View.VISIBLE)
        driverProfileReference!!.addListenerForSingleValueEvent(valueEventListener)
        addPic.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val imageSelectorDialog: ImageSelectorDialog = ImageSelectorDialog((context)!!)
                imageSelectorDialog.setImageName("driver_pic" + MainActivity.Companion.dateStamp)
                imageSelectorDialog.show()
            }
        })
        dob.setOnClickListener(View.OnClickListener {
            val dateDialog: DateDialog = DateDialog((context)!!)
            dateDialog.init(dob)
        })
        dateFirst.setOnClickListener(View.OnClickListener {
            val dateDialog: DateDialog = DateDialog((context)!!)
            dateDialog.init(dateFirst)
        })
        submit_driver.setOnClickListener(submitOnClickListener)
        return view
    }

    var valueEventListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            MainActivity.viewController!!.progress(View.GONE)
            try {
                driverProfileObject =
                    dataSnapshot.child(FirebaseClass.DRIVER_DETAILS_FIREBASE).getValue(
                        DriverProfileObject::class.java
                    )
            } catch (e: Exception) {
                Log.e(TAG, "onDataChange: ", e)
            } finally {
                if (driverProfileObject != null) {
                    driverProfileObject?.apply {
                        forenames.setText(forenames)
                        address.setText(address)
                        postcode.setText(postcode)
                        dob.setText(dob)
                        dateFirst.setText(dateFirst)
                        ni.setText(ni)
                        Picasso.get().load(driverPic)
                            .into(MainActivity.loadImage(driverPic))
                        picUri = Uri.parse(driverPic)
                    }

                }
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            MainActivity.viewController!!.progress(View.GONE)
        }
    }
    var submitOnClickListener: View.OnClickListener = View.OnClickListener {
        val driverForename: String = forenames!!.text.toString().trim { it <= ' ' }
        val AddressString: String = address!!.text.toString().trim { it <= ' ' }
        val postCodeString: String = postcode!!.text.toString().trim { it <= ' ' }
        val dobString: String = dob!!.text.toString().trim { it <= ' ' }
        val niString: String = ni!!.text.toString().trim { it <= ' ' }
        val dateFirstString: String = dateFirst!!.text.toString().trim { it <= ' ' }
        if ((!TextUtils.isEmpty(driverForename) &&
                    !TextUtils.isEmpty(AddressString) &&
                    !TextUtils.isEmpty(postCodeString) &&
                    !TextUtils.isEmpty(dobString) &&
                    !TextUtils.isEmpty(niString) &&
                    !TextUtils.isEmpty(dateFirstString))
        ) {
            if (filePath == null && picUri == null) {
                Toast.makeText(context, "No Driver image", Toast.LENGTH_SHORT).show()
                MainActivity.viewController!!.progress(View.GONE)
            } else {
                MainActivity.viewController!!.progress(View.VISIBLE)
                if (filePath != null) {
                    FirebaseClass(context, filePath, object : FirebaseClass.Response {
                        override fun processFinish(output: Uri?) {
                            Log.i(TAG, "processFinish: ")
                            if (output != null) {
                                picUri = output
                                writeDriverToDb()
                            } else {
                                MainActivity.viewController!!.progress(View.GONE)
                            }
                        }
                    }).uploadImage(
                        FirebaseClass.DRIVERS_LICENSE_FIREBASE,
                        FirebaseClass.DRIVERS_LICENSE_FIREBASE + MainActivity.Companion.dateStamp
                    )
                } else {
                    Log.i(TAG, "onClick: pushing with same image")
                    writeDriverToDb()
                }
            }
        } else {
            if (TextUtils.isEmpty(driverForename)) {
                forenames!!.error = "Field required"
            }
            if (TextUtils.isEmpty(AddressString)) {
                address!!.error = "Field required"
            }
            if (TextUtils.isEmpty(postCodeString)) {
                postcode!!.error = "Field required"
            }
            if (TextUtils.isEmpty(dobString)) {
                dob!!.error = "Field required"
            }
            if (TextUtils.isEmpty(niString)) {
                ni!!.error = "Field required"
            }
            if (TextUtils.isEmpty(dateFirstString)) {
                dateFirst!!.error = "Field required"
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        ImageSelectorResults().Results(
            activity, requestCode, resultCode, data,
            filePath, driverPic, object : FilepathResponse {
                override fun processFinish(output: Uri?) {
                    filePath = output
                }
            })
    }

    private fun writeDriverToDb() {
        val forenameText: String = forenames!!.text.toString().trim({ it <= ' ' })
        val addressText: String = address!!.text.toString().trim({ it <= ' ' })
        val postcodeText: String = postcode!!.text.toString().trim({ it <= ' ' })
        val dobText: String = dob!!.text.toString().trim({ it <= ' ' })
        val niText: String = ni!!.text.toString().trim({ it <= ' ' })
        val datefirstText: String = dateFirst!!.text.toString().trim({ it <= ' ' })
        val driverProfileObject: DriverProfileObject = DriverProfileObject(
            picUri.toString(), forenameText,
            addressText, postcodeText, dobText, niText, datefirstText
        )
        if ((UID == MainActivity.auth!!.currentUser!!.uid)) {
            val profileUpdatesBuilder: UserProfileChangeRequest.Builder =
                UserProfileChangeRequest.Builder()
            profileUpdatesBuilder.setPhotoUri(picUri)
            val profileUpdates: UserProfileChangeRequest = profileUpdatesBuilder.build()
            MainActivity.auth!!.currentUser!!.updateProfile(profileUpdates)
                .addOnCompleteListener(object : OnCompleteListener<Void?> {
                    override fun onComplete(task: Task<Void?>) {
                        if (task.isSuccessful) {
                            Log.d(TAG, "User profile updated.")
                            MainActivity.viewController!!.reloadDrawer()
                            MainActivity.mDatabase!!.child(FirebaseClass.USER_FIREBASE)
                                .child(
                                    MainActivity.auth!!.currentUser!!.uid
                                ).child("user_details")
                                .setValue(
                                    UserObject(
                                        MainActivity.auth!!.currentUser!!
                                            .displayName,
                                        MainActivity.auth!!.currentUser!!
                                            .email,
                                        picUri.toString()
                                    )
                                )
                        }
                    }
                })
                .addOnFailureListener(object : OnFailureListener {
                    override fun onFailure(e: Exception) {
                        Log.e(TAG, "onFailure: ", e)
                    }
                })
        }
        driverProfileReference!!.child(FirebaseClass.DRIVER_DETAILS_FIREBASE)
            .setValue(driverProfileObject)
            .addOnCompleteListener(object : OnCompleteListener<Void?> {
                override fun onComplete(task: Task<Void?>) {
                    if (task.isSuccessful) {
                        MainActivity.approvalsClass!!.setStatusCode(
                            UID,
                            FirebaseClass.DRIVER_DETAILS_FIREBASE + FirebaseClass.APPROVAL_CONSTANT,
                            FirebaseClass.APPROVAL_PENDING
                        )
                    }
                    MainActivity.viewController!!.progress(View.GONE)
                    MainActivity.fragmentManager!!.popBackStack()
                }
            })
    }
}