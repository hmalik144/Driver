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
        val ref = path.child("$filename.jpeg")
        return ref.putFile(localFilePath)
            .continueWithTask { ref.downloadUrl }
            .await()
    }

    suspend fun uploadImageReturnName(localFilePath: Uri, path: StorageReference, filename: String): String {
        val ref = path.child("$filename.jpeg")
        return ref.putFile(localFilePath)
            .continueWith { ref.name }
            .await()
    }

    suspend fun uploadImageReturnRef(localFilePath: Uri, path: StorageReference, filename: String): String {
        val ref = path.child("$filename.jpeg")

        return ref.putFile(localFilePath)
            .continueWith {
                it.result.storage.toString()
            }.await()
    }

    suspend fun uploadImage(localFilePath: Uri, uid: String, storage: Storage) {
        val ref = when(storage) {
            Storage.PROFILE -> profileImageStorageRef(uid)
            Storage.DRIVERS_LICENSE -> driversLicenseStorageRef(uid)
            Storage.INSURANCE -> insuranceStorageRef(uid)
            Storage.LOG_BOOK -> logBookStorageRef(uid)
            Storage.MOT -> motStorageRef(uid)
            Storage.PRIVATE_HIRE -> privateHireStorageRef(uid)
            Storage.PRIVATE_HIRE_VEHICLE -> privateHireVehicleStorageRef(uid)
        }
        uploadImage(localFilePath, ref, storage.label)
    }

    private fun usersImagesStorageRef(uid: String) = storageRef.child(IMAGE_CONST).child(uid)
    fun profileImageStorageRef(uid: String) = usersImagesStorageRef(uid).child(PROFILE_SREF)
    fun driversLicenseStorageRef(uid: String) =
        usersImagesStorageRef(uid).child(DRIVERS_LICENSE_SREF)

    fun insuranceStorageRef(uid: String) = usersImagesStorageRef(uid).child(INSURANCE_SREF)
    fun logBookStorageRef(uid: String) = usersImagesStorageRef(uid).child(LOG_BOOK_SREF)
    fun motStorageRef(uid: String) = usersImagesStorageRef(uid).child(MOT_SREF)
    fun privateHireStorageRef(uid: String) = usersImagesStorageRef(uid).child(PRIVATE_HIRE_SREF)
    fun privateHireVehicleStorageRef(uid: String) =
        usersImagesStorageRef(uid).child(PRIVATE_HIRE_VEHICLE_SREF)

    suspend fun downloadImage(storageReference: StorageReference): Uri? {
        return storageReference.downloadUrl.await()
    }

    suspend fun getFileAndThumbnail(
        ref: StorageReference,
        prefix: String
    ): Pair<StorageReference?, StorageReference?> {
        val items = ref.listAll().await().items

        // get storage refs for documents and thumbnail
        val document = items.lastOrNull { !it.name.contains("thumb_") }
        val thumbnail = items.firstOrNull { it.name.contains("thumb_${document?.name?.split(".")?.get(0)}") }

        return Pair(document, thumbnail)
    }

    suspend fun getMultipleFilesAndThumbnails(
        ref: StorageReference,
        prefix: String
    ): MutableMap<StorageReference, StorageReference?> {
        val items = ref.listAll().await().items
        val map = mutableMapOf<StorageReference, StorageReference?>()

        // load map with document and thumbnail map sets
        items.filter { it.name.startsWith(prefix) }.forEach { s ->
            val value = items.firstOrNull { it.name.contains("thumb_${s?.name}") }
            map[s] = value
        }

        return map
    }
}