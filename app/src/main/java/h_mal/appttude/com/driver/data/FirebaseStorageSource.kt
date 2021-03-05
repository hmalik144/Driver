package h_mal.appttude.com.driver.data

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

private const val IMAGE_CONST = "images"
const val PROFILE_SREF = "user_profile"
const val DRIVERS_LICENSE_SREF = "drivers_license"
const val INSURANCE_SREF = "insurance_details"
const val LOG_BOOK_SREF = "log_book"
const val MOT_SREF = "mot_Details"
const val PRIVATE_HIRE_SREF = "private_hire"
const val PRIVATE_HIRE_VEHICLE_SREF = "private_hire_vehicle"
class FirebaseStorageSource {
    private val storage = FirebaseStorage.getInstance()
    private val storageRef: StorageReference by lazy { storage.reference }

    suspend fun uploadImage(localFilePath: Uri, path: StorageReference, filename: String): Uri {
        val ref = path.child("$filename.jpg")
        return ref.putFile(localFilePath)
            .continueWithTask { ref.downloadUrl }
            .await()
    }

    private fun usersImagesStorageRef(uid: String) = storageRef.child(IMAGE_CONST).child(uid)
    fun profileImageStorageRef(uid: String) = usersImagesStorageRef(uid).child(PROFILE_SREF)
    fun driversLicenseStorageRef(uid: String) = usersImagesStorageRef(uid).child(DRIVERS_LICENSE_SREF)
    fun insuranceStorageRef(uid: String) = usersImagesStorageRef(uid).child(INSURANCE_SREF)
    fun logBookStorageRef(uid: String) = usersImagesStorageRef(uid).child(LOG_BOOK_SREF)
    fun motStorageRef(uid: String) = usersImagesStorageRef(uid).child(MOT_SREF)
    fun privateHireStorageRef(uid: String) = usersImagesStorageRef(uid).child(PRIVATE_HIRE_SREF)
    fun privateHireVehicleStorageRef(uid: String) = usersImagesStorageRef(uid).child(PRIVATE_HIRE_VEHICLE_SREF)
}