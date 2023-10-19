package h_mal.appttude.com.driver.base

import android.net.Uri
import com.google.android.gms.tasks.Tasks
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.ImageDocumentFile
import h_mal.appttude.com.driver.data.ImageResults
import h_mal.appttude.com.driver.data.Storage
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.DateUtils
import h_mal.appttude.com.driver.utils.isNotNull
import kotlinx.coroutines.tasks.await


abstract class DataSubmissionViewModel2<T : ImageDocument>(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    private val storage: FirebaseStorageSource,
    private val storageType: Storage
) : DataSubmissionViewModel<T>(auth, database) {

    abstract val storageRef: StorageReference

    // retrieve image and thumbnail as a pair
    fun getImageAndThumbnail(filename: String): ImageResults {
        val thumbnail = StringBuilder()
            .append("thumb_")
            .append(filename.split(".")[0])
            .append(".png")
            .toString()

        return ImageResults(
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

    fun postDataToDatabase(localImageUri: Uri?, data: T) {
        io {
            val documentFileName = data.getImageFileName()
            // Validate at least image selector image or previously uploaded documents photo available
            if (localImageUri == null && documentFileName.isNullOrEmpty()) {
                onError("Please select an image")
                return@io
            }
            // Upload a new image or keep old image
            val fileName = localImageUri?.let { uploadImage(it, storageType) } ?: documentFileName

            fileName.isNotNull {
                data.setImageFileName(it)
                super.postDataToDatabase(data)
                return@io
            }
            onError("Could not upload document")
        }
    }

    override fun postDataToDatabase(data: T) {}

    override fun setData(data: T) {
        val fileName = data.getImageFileName()
        io {
            fileName?.let {
                val pair = getImageAndThumbnail(it)

                val img = if (pair.image?.downloadUrl?.await() != null) pair.image else null
                val tmb = if (pair.thumbnail?.downloadUrl?.await() != null) pair.image else null

                val image = ImageResults(img, tmb)

                val document = ImageDocumentFile(data, image)
                onSuccess(document)
            }
            super.setData(data)
        }

    }
}