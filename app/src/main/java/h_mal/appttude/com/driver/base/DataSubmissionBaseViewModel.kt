package h_mal.appttude.com.driver.base

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseCompletion
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.DateUtils.getDateTimeStamp
import h_mal.appttude.com.driver.utils.getDataFromDatabaseRef
import kotlinx.coroutines.Job
import java.io.IOException
import java.lang.NullPointerException


abstract class DataSubmissionBaseViewModel<T : Any>(
    auth: FirebaseAuthentication,
    private val database: FirebaseDatabaseSource,
    private val storage: FirebaseStorageSource?
) : BaseViewModel() {
    val stateLiveData = auth.userStateListener()
    val uid: String = auth.getUid() ?: ""

    abstract val databaseRef: DatabaseReference
    abstract val storageRef: StorageReference?
    abstract val objectName: String

    abstract fun getDataFromDatabase(): Job?
    open fun setDataInDatabase(data: T, localImageUri: Uri?): Job = Job()
    open fun setDataInDatabase(data: T, localImageUris: List<Uri?>?): Job = Job()
    open fun setDataInDatabase(data: T) {}

    inline fun <reified T : Any> retrieveDataFromDatabase() = io {
        doTryOperation("Failed to retrieve $objectName") {
            val data = databaseRef.getDataFromDatabaseRef<T>()
            onSuccess(data ?: FirebaseCompletion.Default)
        }
    }

    suspend fun <T : Any> postDataToDatabase(data: T) {
        val driversLicense = database.postToDatabaseRed(databaseRef, data)
        onSuccess(driversLicense)
    }

    private suspend fun uploadImage(localImageUri: Uri?): String? {
        val imageString = StringBuilder()
            .append(getDateTimeStamp())
            .append("_")
            .append(objectName.replace(" ", "_"))
            .toString()

        return localImageUri?.let { uri ->
            storageRef?.let {
                val image = storage?.uploadImage(uri, it, imageString)
                image.toString()
            }
        }
    }

    suspend fun getImageUrl(localImageUri: Uri?, imageUrl: String?): String {
        if (localImageUri == null && imageUrl.isNullOrBlank()) {
            throw IOException("No image is selected")
        }

        return uploadImage(localImageUri) ?: imageUrl!!
    }

    suspend fun getImageUrls(localImageUris: List<Uri?>?): List<String?> {
        if (localImageUris.isNullOrEmpty()) {
            throw IOException("No images is selected")
        }
        val listOfUrls = mutableListOf<String>()
        localImageUris.forEach { uri ->
            uploadImage(uri)?.let {
                listOfUrls.add(it)
            }
        }

        return listOfUrls
    }

    fun downloadImageAndThumbnail(prefix: String) {
        if (storageRef == null) throw NullPointerException("No image(s) are available to retrieve")

        io {
            doTryOperation("Failed to retrieve image") {
                val urlPair = storageRef!!.let { storage!!.getFileAndThumbnail(it, prefix) }
                onSuccess(urlPair)
            }
        }
    }

    fun downloadMultipleImageAndThumbnail(prefix: String) {
        if (storageRef == null) throw NullPointerException("No image(s) are available to retrieve")

        io {
            doTryOperation("Failed to retrieve image") {
                val urlMap = storageRef!!.let { storage!!.getMultipleFilesAndThumbnails(it, prefix) }
                onSuccess(urlMap)
            }
        }
    }


    fun getImageAndThumbnail(filename: String): Pair<StorageReference, StorageReference> {
        if (storageRef == null) throw NullPointerException("No image(s) are available to retrieve")

        val thumbnail = StringBuilder()
            .append("thumb_")
            .append(filename.split(".")[0])
            .append(".png")
            .toString()

        return Pair(
            storageRef!!.child(filename),
            storageRef!!.child(thumbnail)
        )
    }

    fun getMultipleImagesAndThumbnails(filenames: MutableList<String>): Map<StorageReference, StorageReference> {
        return filenames.associate { getImageAndThumbnail(it) }
    }

}