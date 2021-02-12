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
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import h_mal.appttude.com.driver.Global.*
import h_mal.appttude.com.driver.Global.ImageSelectorResults.FilepathResponse
import h_mal.appttude.com.driver.MainActivity
import h_mal.appttude.com.driver.Objects.InsuranceObject
import h_mal.appttude.com.driver.R
import java.util.*


class InsuranceFragment : Fragment() {
    private val TAG: String = this.javaClass.simpleName
    var reference: DatabaseReference? = null
    var uploadIns: TextView? = null
    var insName: EditText? = null
    var insExpiry: EditText? = null
    var holder: View? = null
    var filePath: Uri? = null
    var picUri: Uri? = null
    var photoStrings: MutableList<String?>? = null
    var insNameString: String? = null
    var insExpiryString: String? = null
    var insuranceObject: InsuranceObject? = null
    var swiperClass: ImageSwiperClass? = null
    var uploadNew: Boolean? = null
    var UID: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uploadNew = false
        photoStrings = ArrayList()
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
            ).child(FirebaseClass.VEHICLE_FIREBASE)
                .child(FirebaseClass.INSURANCE_FIREBASE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_insurance, container, false)
        uploadIns = view.findViewById(R.id.uploadInsurance)
        insName = view.findViewById(R.id.insurer)
        insExpiry = view.findViewById(R.id.insurance_exp)
        val submit: Button = view.findViewById(R.id.submit_ins)
        holder = view.findViewById(R.id.image_pager)
        swiperClass = ImageSwiperClass(context, holder)
        MainActivity.viewController!!.progress(View.VISIBLE)
        reference!!.addListenerForSingleValueEvent(valueEventListener)
        uploadIns.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val imageSelectorDialog: ImageSelectorDialog = ImageSelectorDialog((context)!!)
                imageSelectorDialog.setImageName("insurance" + MainActivity.Companion.dateStamp)
                imageSelectorDialog.show()
            }
        })
        insExpiry.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val dateDialog: DateDialog = DateDialog((context)!!)
                dateDialog.init(insExpiry)
            }
        })
        submit.setOnClickListener(submitOnClickListener)
        return view
    }

    var valueEventListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            MainActivity.viewController!!.progress(View.GONE)
            try {
                insuranceObject = dataSnapshot.getValue(InsuranceObject::class.java)
            } catch (e: Exception) {
                Log.e(TAG, "onDataChange: ", e)
            } finally {
                if (insuranceObject != null) {
                    if (!uploadNew!!) {
                        photoStrings = insuranceObject.getPhotoStrings()
                        swiperClass!!.reinstantiateList(photoStrings)
                        if (insuranceObject!!.insurerName != null) {
                            insNameString = insuranceObject.getInsurerName()
                            insName!!.setText(insNameString)
                        }
                        if (insuranceObject!!.expiryDate != null) {
                            insExpiryString = insuranceObject.getExpiryDate()
                            insExpiry!!.setText(insExpiryString)
                        }
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
            insNameString = insName!!.text.toString().trim({ it <= ' ' })
            insExpiryString = insExpiry!!.text.toString().trim({ it <= ' ' })
            if ((!TextUtils.isEmpty(insNameString)
                        && !TextUtils.isEmpty(insExpiryString))
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
                                        "Could not upload",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    MainActivity.viewController!!.progress(View.GONE)
                                }
                            }
                        }).uploadImage(
                            FirebaseClass.INSURANCE_FIREBASE,
                            FirebaseClass.INSURANCE_FIREBASE + MainActivity.Companion.dateStamp
                        )
                    } else {
                        Log.i(TAG, "onClick: pushing with same image")
                        publishObject()
                    }
                }
            } else {
                if (TextUtils.isEmpty(insNameString)) {
                    insName!!.error = "Field required"
                }
                if (TextUtils.isEmpty(insExpiryString)) {
                    insExpiry!!.error = "Field required"
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

    private fun publishObject() {
        if ((uploadNew)!!) {
            MainActivity.archiveClass!!.archiveRecord(
                UID,
                FirebaseClass.INSURANCE_FIREBASE,
                insuranceObject
            )
        }
        photoStrings = swiperClass.getImageStrings()
        val insuranceObject: InsuranceObject =
            InsuranceObject(photoStrings, insNameString, insExpiryString)
        reference!!.setValue(insuranceObject)
            .addOnCompleteListener(object : OnCompleteListener<Void?> {
                override fun onComplete(task: Task<Void?>) {
                    if (task.isSuccessful) {
                        MainActivity.approvalsClass!!.setStatusCode(
                            UID,
                            FirebaseClass.INSURANCE_FIREBASE + FirebaseClass.APPROVAL_CONSTANT,
                            FirebaseClass.APPROVAL_PENDING
                        )
                        MainActivity.fragmentManager!!.popBackStack()
                    } else {
                        Toast.makeText(context, "Upload Unsuccessful", Toast.LENGTH_SHORT)
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
            filePath, object : FilepathResponse {
                override fun processFinish(output: Uri?) {
                    filePath = output
                    FirebaseClass(context, output, object : FirebaseClass.Response {
                        override fun processFinish(output: Uri?) {
                            if (output != null) {
                                photoStrings!!.add(output.toString())
                                swiperClass!!.addPhotoString(output.toString())
                                //notify data change
                                reference!!.setValue(InsuranceObject(photoStrings, null, null))
                            }
                        }
                    }).uploadImage(
                        FirebaseClass.INSURANCE_FIREBASE,
                        FirebaseClass.INSURANCE_FIREBASE + MainActivity.Companion.dateStamp
                    )
                }
            })
    }

    override fun onResume() {
        super.onResume()
        MainActivity.printObjectAsJson(TAG, photoStrings)
    }
}