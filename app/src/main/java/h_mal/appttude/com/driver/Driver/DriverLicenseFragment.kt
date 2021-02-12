package h_mal.appttude.com.driver.Driver

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import h_mal.appttude.com.driver.Global.*
import h_mal.appttude.com.driver.Global.ImageSelectorResults.FilepathResponse
import h_mal.appttude.com.driver.MainActivity
import h_mal.appttude.com.driver.Objects.DriversLicenseObject
import h_mal.appttude.com.driver.R
import kotlinx.android.synthetic.main.fragment_driver_license.*

class DriverLicenseFragment : Fragment() {
    private val TAG: String = this.javaClass.simpleName
    private var imageView: ImageView? = null
    var licenseNo: EditText? = null
    var expiry: EditText? = null
    var filePath: Uri? = null
    var picUri: Uri? = null
    var li_numberString: String? = null
    var li_exprString: String? = null
    var reference: DatabaseReference? = null
    var driversLicenseObject: DriversLicenseObject? = null
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
        reference =
            MainActivity.mDatabase!!.child(FirebaseClass.USER_FIREBASE).child(
                (UID)!!
            )
                .child(FirebaseClass.DRIVER_FIREBASE)
                .child(FirebaseClass.DRIVERS_LICENSE_FIREBASE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_driver_license, container, false)
        imageView = view.findViewById(R.id.driversli_img)
        MainActivity.viewController!!.progress(View.VISIBLE)
        reference!!.addListenerForSingleValueEvent(valueEventListener)
        val uploadLic: TextView = view.findViewById(R.id.upload_lic)
        licenseNo = view.findViewById(R.id.lic_no)
        lic_expiry.setOnClickListener {
            val dateDialog = DateDialog((context)!!)
            dateDialog.init(lic_expiry)
        }
        val submit: Button = view.findViewById(R.id.submit)
        submit.setOnClickListener(submitOnClickListener)
        uploadLic.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val imageSelectorDialog: ImageSelectorDialog = ImageSelectorDialog((context)!!)
                imageSelectorDialog.setImageName("drivers_license")
                imageSelectorDialog.show()
            }
        })
        return view
    }

    var submitOnClickListener: View.OnClickListener = View.OnClickListener {
        li_numberString = licenseNo!!.text.toString().trim { it <= ' ' }
        li_exprString = lic_expiry!!.text.toString().trim { it <= ' ' }
        if (!TextUtils.isEmpty(li_numberString) &&
            !TextUtils.isEmpty(li_exprString)
        ) {
            MainActivity.viewController!!.progress(View.VISIBLE)
            if (filePath == null && picUri == null) {
                Toast.makeText(context, "No Driver image", Toast.LENGTH_SHORT).show()
                MainActivity.viewController!!.progress(View.GONE)
            } else {
                if (filePath != null) {
                    Log.i(TAG, "onClick: new Image uploaded")
                    FirebaseClass(context, filePath, object : FirebaseClass.Response {
                        override fun processFinish(output: Uri?) {
                            Log.i(TAG, "processFinish: ")
                            if (output != null) {
                                picUri = output
                                publishObject()
                            } else {
                                Toast.makeText(
                                    context,
                                    getString(R.string.failed_upload),
                                    Toast.LENGTH_SHORT
                                ).show()
                                MainActivity.viewController!!.progress(View.GONE)
                            }
                        }
                    }).uploadImage(
                        FirebaseClass.DRIVERS_LICENSE_FIREBASE,
                        FirebaseClass.DRIVERS_LICENSE_FIREBASE + MainActivity.dateStamp
                    )
                } else {
                    Log.i(TAG, "onClick: pushing with same image")
                    publishObject()
                }
            }
        } else {
            if (TextUtils.isEmpty(li_numberString)) {
                licenseNo!!.error = "Field required"
            }
            if (TextUtils.isEmpty(li_exprString)) {
                lic_expiry!!.error = "Field required"
            }
            if (picUri == null && filePath == null) {
                Toast.makeText(
                    context,
                    getString(R.string.image_required),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    var valueEventListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            MainActivity.viewController!!.progress(View.GONE)
            try {
                driversLicenseObject = dataSnapshot.getValue(
                    DriversLicenseObject::class.java
                )
            } catch (e: Exception) {
                Log.e(TAG, "onDataChange: ", e)
            } finally {
                if (driversLicenseObject != null) {
                    picUri = Uri.parse(driversLicenseObject!!.licenseImageString)
                    li_numberString = driversLicenseObject!!.licenseNumber
                    li_exprString = driversLicenseObject!!.licenseExpiry
                    if (!uploadNew!!) {
                        licenseNo!!.setText(li_numberString)
                        lic_expiry!!.setText(li_exprString)
                        Picasso.get()
                            .load(picUri)
                            .into(MainActivity.loadImage(imageView))
                    }
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.failed_retrieve),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            MainActivity.viewController!!.progress(View.GONE)
            Toast.makeText(context, databaseError.message, Toast.LENGTH_SHORT).show()
            Log.e(TAG, "onCancelled: " + databaseError.message)
        }
    }

    private fun publishObject() {
        if ((uploadNew)!!) {
            MainActivity.archiveClass!!.archiveRecord(
                UID,
                FirebaseClass.DRIVERS_LICENSE_FIREBASE,
                driversLicenseObject
            )
        }
        val driversLicenseObjectNew: DriversLicenseObject =
            DriversLicenseObject(picUri.toString(), li_numberString, li_exprString)
        reference!!.setValue(driversLicenseObjectNew)
            .addOnCompleteListener(object : OnCompleteListener<Void?> {
                override fun onComplete(task: Task<Void?>) {
                    if (task.isSuccessful) {
                        Log.i(TAG, "onComplete: publish = " + task.isSuccessful)
                        MainActivity.approvalsClass!!.setStatusCode(
                            UID,
                            FirebaseClass.DRIVERS_LICENSE_FIREBASE + FirebaseClass.APPROVAL_CONSTANT,
                            FirebaseClass.APPROVAL_PENDING
                        )
                        MainActivity.fragmentManager!!.popBackStack()
                    } else {
                        Toast.makeText(context, R.string.unsuccessful, Toast.LENGTH_SHORT)
                            .show()
                    }
                    MainActivity.viewController!!.progress(View.GONE)
                }
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ImageSelectorDialog.MY_CAMERA_PERMISSION_CODE) {
            if (grantResults.get(0) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "camera permission granted", Toast.LENGTH_LONG).show()
                val cameraIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, ImageSelectorDialog.CAMERA_REQUEST)
            } else {
                Toast.makeText(context, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        ImageSelectorResults().Results(
            activity, requestCode, resultCode, data,
            filePath, imageView, object : FilepathResponse {
                override fun processFinish(output: Uri?) {
                    filePath = output
                }
            })
    }
}