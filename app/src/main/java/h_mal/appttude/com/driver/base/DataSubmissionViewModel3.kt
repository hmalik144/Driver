package h_mal.appttude.com.driver.base

import android.net.Uri
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.Storage
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.DateUtils
import h_mal.appttude.com.driver.utils.mapIndexSuspend


abstract class DataSubmissionViewModel3<T : Any>(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    private val storage: FirebaseStorageSource
) : DataSubmissionViewModel<T>(auth, database) {

    abstract val storageRef: StorageReference

    fun getImagesAndThumbnails(filenames: List<String>): List<Pair<StorageReference, StorageReference>> {
        return filenames.map {
            val thumbnail = StringBuilder()
                .append("thumb_")
                .append(it.split(".")[0])
                .append(".png")
                .toString()

            Pair(
                storageRef.child(it),
                storageRef.child(thumbnail)
            )
        }
    }

    suspend fun uploadImages(localImageUris: List<Uri>, storages: Storage): List<String> {
        return localImageUris.mapIndexSuspend { index, uri ->
            val imageString = StringBuilder()
                .append(DateUtils.getDateTimeStamp())
                .append("_")
                .append(storages.label)
                .append("_$index")
                .toString()

            storage.uploadImageReturnName(uri, storageRef, imageString)
        }
    }

    fun postDataToDatabase(localImageUris: List<Uri>, data: T, storages: Storage) {
        io {
            val files = uploadImages(localImageUris, storages)
            val postData = setDataAfterUpload(data, files)

            super.postDataToDatabase(postData)
        }
    }

    abstract fun setDataAfterUpload(data: T, filename: List<String>): T

}