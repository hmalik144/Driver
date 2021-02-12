package h_mal.appttude.com.driver.Global

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import h_mal.appttude.com.driver.MainActivity
import h_mal.appttude.com.driver.utils.displayToast

class FirebaseClass constructor(var context: Context?, var filePath: Uri?, var delegate: Response) {
    open interface Response {
        fun processFinish(output: Uri?)
    }

    fun uploadImage(path: String, name: String) {
        if (filePath != null) {
            val progressDialog: ProgressDialog = ProgressDialog(context)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            val ref: StorageReference = MainActivity.storageReference!!.child(
                ("images/" + MainActivity.auth!!.currentUser!!
                    .uid + "/" + path
                        + "/" + name)
            )
            val uploadTask: UploadTask = ref.putFile(filePath!!)
            uploadTask.addOnProgressListener(object : OnProgressListener<UploadTask.TaskSnapshot> {
                override fun onProgress(taskSnapshot: UploadTask.TaskSnapshot) {
                    val progress: Double =
                        (100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                            .totalByteCount)
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
            }).continueWithTask(object : Continuation<UploadTask.TaskSnapshot?, Task<Uri>> {
                @Throws(Exception::class)
                override fun then(task: Task<UploadTask.TaskSnapshot?>): Task<Uri> {
                    if (!task.isSuccessful) {
                        throw (task.exception)!!
                    }

                    // Continue with the task to get the download URL
                    return ref.downloadUrl
                }
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    delegate.processFinish(task.result)
                    progressDialog.dismiss()
                    context?.displayToast("Uploaded Successfully")
                    println("onComplete: uploaded Successful uri: " + task.result)
                } else {
                    delegate.processFinish(null)
                    progressDialog.dismiss()
                    context?.displayToast("Uploaded Successfully")
                }
            }
        }
    }

    companion object {
        val USER_FIREBASE: String = "user"
        val DRIVER_FIREBASE: String = "driver_profile"
        val DRIVER_DETAILS_FIREBASE: String = "driver_details"
        val PRIVATE_HIRE_FIREBASE: String = "private_hire"
        val DRIVERS_LICENSE_FIREBASE: String = "driver_license"
        val DRIVER_STATUS: String = "driver_status"
        val USER_APPROVALS: String = "approvalsObject"
        val APPROVAL_CONSTANT: String = "_approval"
        val ARCHIVE_FIREBASE: String = "archive"
        val DRIVER_NUMBER: String = "driver_number"
        val VEHICLE_FIREBASE: String = "vehicle_profile"
        val MOT_FIREBASE: String = "mot_details"
        val VEHICLE_DETAILS_FIREBASE: String = "vehicle_details"
        val INSURANCE_FIREBASE: String = "insurance_details"
        val LOG_BOOK_FIREBASE: String = "log_book"
        val PRIVATE_HIRE_VEHICLE_LICENSE: String = "private_hire_vehicle"
        val NO_DATE_PRESENT: Int = 0
        val APPROVAL_PENDING: Int = 1
        val APPROVAL_DENIED: Int = 2
        val APPROVED: Int = 3
    }
}