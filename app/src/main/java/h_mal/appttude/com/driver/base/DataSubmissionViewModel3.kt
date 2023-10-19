package h_mal.appttude.com.driver.base

import android.net.Uri
import com.google.android.gms.tasks.Tasks
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.ImageCollection
import h_mal.appttude.com.driver.data.Storage
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.DateUtils
import h_mal.appttude.com.driver.utils.isNotNull
import h_mal.appttude.com.driver.utils.mapIndexSuspend


abstract class DataSubmissionViewModel3<T : MultiImageDocument>(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    private val storage: FirebaseStorageSource,
    private val storageType: Storage
) : DataSubmissionViewModel<T>(auth, database) {

    abstract val storageRef: StorageReference

    fun getImagesAndThumbnails(filenames: List<String>): ImageCollection {
        val listMap = filenames.map {
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
        return ImageCollection(listMap)
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

    fun postDataToDatabase(localImageUris: List<Uri>, data: T) {
        io {
            // Validate document fore upload
            if (!validateData(data)) return@io
            val documentFileNames = data.getImageFileNames()
            // Validate at least image selector image or previously uploaded documents photo available
            if (localImageUris.isEmpty() && documentFileNames.isNullOrEmpty()) {
                onError("Please select an image")
                return@io
            }
            // Upload a new image or keep old image
            val fileNames =
                localImageUris.takeIf { it.isNotEmpty() }?.let { uploadImages(it, storageType) }
                    ?: documentFileNames

            fileNames.isNotNull {
                data.setImageFileNames(it)
                super.postDataToDatabase(data)
                return@io
            }
            onError("Could not upload document")
        }
    }

    override fun setData(data: T) {
        val fileNames = data.getImageFileNames()
        fileNames?.let { l ->
            val list = getImagesAndThumbnails(l).collection

            val results = list.map { it.second.downloadUrl }.let { Tasks.whenAllComplete(it) }
            results.addOnSuccessListener {
                onSuccess(list)
            }
        }
        super.setData(data)
    }

    override fun postDataToDatabase(data: T) {}

}