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
import h_mal.appttude.com.driver.Objects.PrivateHireVehicleObject
import h_mal.appttude.com.driver.R


class PrivateHireVehicleFragment : Fragment() {
    private val TAG: String = this.javaClass.simpleName
    private var imageView: ImageView? = null
    var phNo: EditText? = null
    var phExpiry: EditText? = null
    var filePath: Uri? = null
    var picUri: Uri? = null
    var Ph_numberString: String? = null
    var Ph_exprString: String? = null
    var reference: DatabaseReference? = null
    var privateHireObject: PrivateHireVehicleObject? = null
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
                .child(FirebaseClass.VEHICLE_FIREBASE)
                .child(FirebaseClass.PRIVATE_HIRE_VEHICLE_LICENSE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_private_hire_vehicle, container, false)
        MainActivity.viewController!!.progress(View.VISIBLE)
        reference!!.addListenerForSingleValueEvent(valueEventListener)
        val uploadPH: TextView = view.findViewById(R.id.uploadphlic)
        imageView = view.findViewById(R.id.imageView2)
        phNo = view.findViewById(R.id.ph_no)
        phExpiry = view.findViewById(R.id.ph_expiry)
        phExpiry.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val dateDialog: DateDialog = DateDialog((context)!!)
                dateDialog.init(phExpiry)
                dateDialog.show()
            }
        })
        val submit: Button = view.findViewById(R.id.submit)
        submit.setOnClickListener(submitClick)
        uploadPH.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val imageSelectorDialog: ImageSelectorDialog = ImageSelectorDialog((context)!!)
                imageSelectorDialog.setImageName("private_hire")
                imageSelectorDialog.show()
            }
        })
        return view
    }

    private val submitClick: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View) {
            Ph_numberString = phNo!!.text.toString().trim({ it <= ' ' })
            Ph_exprString = phExpiry!!.text.toString().trim({ it <= ' ' })

            //validation for data then submit
            if (!TextUtils.isEmpty(Ph_numberString) &&
                !TextUtils.isEmpty(Ph_exprString)
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
                                        R.string.unsuccessful,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    MainActivity.viewController!!.progress(View.GONE)
                                }
                            }
                        }).uploadImage(
                            FirebaseClass.PRIVATE_HIRE_FIREBASE,
                            FirebaseClass.PRIVATE_HIRE_FIREBASE + MainActivity.Companion.dateStamp
                        )
                    } else {
                        Log.i(TAG, "onClick: pushing with same image")
                        publishObject()
                    }
                }
            } else {
                if (TextUtils.isEmpty(Ph_numberString)) {
                    phNo!!.error = "Field required"
                }
                if (TextUtils.isEmpty(Ph_exprString)) {
                    phExpiry!!.error = "Field required"
                }
                if (picUri == null) {
                    Toast.makeText(
                        context,
                        getString(R.string.image_required),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    var valueEventListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            MainActivity.viewController!!.progress(View.GONE)
            try {
                privateHireObject = dataSnapshot.getValue(
                    PrivateHireVehicleObject::class.java
                )
            } catch (e: Exception) {
                Log.e(TAG, "onDataChange: ", e)
            } finally {
                if (privateHireObject != null) {
                    picUri = Uri.parse(privateHireObject.getPhCarImageString())
                    Ph_numberString = privateHireObject.getPhCarNumber()
                    Ph_exprString = privateHireObject.getPhCarExpiry()
                    Log.i(TAG, "onDataChange: uploadNew = " + uploadNew)
                    if (!uploadNew!!) {
                        phNo!!.setText(Ph_numberString)
                        phExpiry!!.setText(Ph_exprString)
                        Picasso.get()
                            .load(picUri)
                            .into(MainActivity.loadImage(imageView))
                    }
                }
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            MainActivity.viewController!!.progress(View.GONE)
        }
    }

    private fun publishObject() {
        if ((uploadNew)!!) {
            MainActivity.archiveClass!!.archiveRecord(
                UID,
                FirebaseClass.PRIVATE_HIRE_VEHICLE_LICENSE,
                privateHireObject
            )
        }
        val privateHireObjectNew: PrivateHireVehicleObject =
            PrivateHireVehicleObject(picUri.toString(), Ph_numberString, Ph_exprString)
        reference!!.setValue(privateHireObjectNew)
            .addOnCompleteListener(object : OnCompleteListener<Void?> {
                override fun onComplete(task: Task<Void?>) {
                    if (task.isSuccessful) {
                        Log.i(TAG, "onComplete: publish = " + task.isSuccessful)
                        MainActivity.approvalsClass!!.setStatusCode(
                            UID,
                            FirebaseClass.PRIVATE_HIRE_VEHICLE_LICENSE + FirebaseClass.APPROVAL_CONSTANT,
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