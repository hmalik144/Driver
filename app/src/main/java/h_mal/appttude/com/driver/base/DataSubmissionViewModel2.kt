package h_mal.appttude.com.driver.base

import android.net.Uri
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.Storage
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.DateUtils


abstract class DataSubmissionViewModel2<T : Any>(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    private val storage: FirebaseStorageSource
) : DataSubmissionViewModel<T>(auth, database) {

    abstract val storageRef: StorageReference

    // retrieve image and thumbnail as a pair
    fun getImageAndThumbnail(filename: String): Pair<StorageReference, StorageReference> {
        val thumbnail = StringBuilder()
            .append("thumb_")
            .append(filename.split(".")[0])
            .append(".png")
            .toString()

        return Pair(
            storageRef.child(filename),
            storageRef.child(thumbnail)
        )
    }

    suspend fun uploadImage(localImageUri: Uri, storages: Storage): String {
        val imageString = StringBuilder()
            .append(DateUtils.getDateTimeStamp())
            .append("_")
            .append(storages.label)
            .toString()

        return storage.uploadImageReturnName(localImageUri, storageRef, imageString)
    }

    fun postDataToDatabase(localImageUri: Uri, data: T, storages: Storage) {
        io {
            val fileName = uploadImage(localImageUri, storages)
            val afterData = setDataAfterUpload(data, fileName)

            super.postDataToDatabase(afterData)
        }
    }

    abstract fun setDataAfterUpload(data: T, filename: String): T
}