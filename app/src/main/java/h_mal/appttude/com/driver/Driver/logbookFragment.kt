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
import h_mal.appttude.com.driver.Global.ExecuteFragment
import h_mal.appttude.com.driver.Global.FirebaseClass
import h_mal.appttude.com.driver.Global.ImageSelectorDialog
import h_mal.appttude.com.driver.Global.ImageSelectorResults
import h_mal.appttude.com.driver.Global.ImageSelectorResults.FilepathResponse
import h_mal.appttude.com.driver.MainActivity
import h_mal.appttude.com.driver.Objects.LogbookObject
import h_mal.appttude.com.driver.R


class logbookFragment : Fragment() {
    private val TAG: String = this.javaClass.simpleName
    var reference: DatabaseReference? = null
    var uploadlb: TextView? = null
    var lbImage: ImageView? = null
    var v5cNumber: EditText? = null
    var filePath: Uri? = null
    var picUri: Uri? = null
    var v5cString: String? = null
    var logbookObject: LogbookObject? = null
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
            ).child(FirebaseClass.VEHICLE_FIREBASE)
                .child(FirebaseClass.LOG_BOOK_FIREBASE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_logbook, container, false)
        uploadlb = view.findViewById(R.id.upload_lb)
        lbImage = view.findViewById(R.id.log_book_img)
        v5cNumber = view.findViewById(R.id.v5c_no)
        val submit: Button = view.findViewById(R.id.submit_lb)
        MainActivity.viewController!!.progress(View.VISIBLE)
        reference!!.addListenerForSingleValueEvent(valueEventListener)
        uploadlb.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val imageSelectorDialog: ImageSelectorDialog = ImageSelectorDialog((context)!!)
                imageSelectorDialog.setImageName("logbook_pic" + MainActivity.Companion.dateStamp)
                imageSelectorDialog.show()
            }
        })
        submit.setOnClickListener(submitOnClickListener)
        return view
    }

    var valueEventListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            MainActivity.viewController!!.progress(View.GONE)
            try {
                logbookObject = dataSnapshot.getValue(LogbookObject::class.java)
            } catch (e: Exception) {
                Log.e(TAG, "onDataChange: ", e)
            } finally {
                if (logbookObject != null) {
                    picUri = Uri.parse(logbookObject.getPhotoString())
                    v5cString = logbookObject.getV5cnumber()
                    if (!uploadNew!!) {
                        v5cNumber!!.setText(v5cString)
                        Picasso.get()
                            .load(picUri)
                            .into(MainActivity.loadImage(lbImage))
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
            v5cString = v5cNumber!!.text.toString().trim({ it <= ' ' })
            if (!TextUtils.isEmpty(v5cString)) {
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
                            FirebaseClass.LOG_BOOK_FIREBASE,
                            FirebaseClass.LOG_BOOK_FIREBASE + MainActivity.Companion.dateStamp
                        )
                    } else {
                        Log.i(TAG, "onClick: pushing with same image")
                        publishObject()
                    }
                }
            } else {
                if (TextUtils.isEmpty(v5cString)) {
                    v5cNumber!!.error = "Field required"
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
                FirebaseClass.LOG_BOOK_FIREBASE,
                logbookObject
            )
        }
        val logbookObjectNew: LogbookObject = LogbookObject(picUri.toString(), v5cString)
        reference!!.setValue(logbookObjectNew)
            .addOnCompleteListener(object : OnCompleteListener<Void?> {
                override fun onComplete(task: Task<Void?>) {
                    if (task.isSuccessful) {
                        MainActivity.approvalsClass!!.setStatusCode(
                            UID,
                            FirebaseClass.LOG_BOOK_FIREBASE + FirebaseClass.APPROVAL_CONSTANT,
                            FirebaseClass.APPROVAL_PENDING
                        )
                        MainActivity.fragmentManager!!.popBackStack()
                    } else {
                        Toast.makeText(context, "Upload Unsuccessful", Toast.LENGTH_SHORT)
                            .show()
                    }
                    MainActivity.viewController!!.progress(View.VISIBLE)
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
            filePath, lbImage, object : FilepathResponse {
                override fun processFinish(output: Uri?) {
                    filePath = output
                }
            })
    }
}