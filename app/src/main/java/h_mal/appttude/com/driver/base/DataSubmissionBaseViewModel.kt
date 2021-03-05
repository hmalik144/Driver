package h_mal.appttude.com.driver.base

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.data.FirebaseCompletion
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.DateUtils.getDateTimeStamp
import h_mal.appttude.com.driver.utils.getDataFromDatabaseRef
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.io.IOException

abstract class DataSubmissionBaseViewModel<T : Any>(
    private val database: FirebaseDatabaseSource,
    private val storage: FirebaseStorageSource?
) : BaseViewModel() {

    abstract val databaseRef: DatabaseReference
    abstract val storageRef: StorageReference?
    abstract val objectName: String

    abstract fun getDataFromDatabase(): Job
    open fun setDataInDatabase(data: T, localImageUri: Uri?): Job = Job()
    open fun setDataInDatabase(data: T, localImageUris: List<Uri?>?): Job = Job()
    open fun setDataInDatabase(data: T) {  }

    inline fun <reified T : Any> getDataClass() = io {
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
        if (localImageUri == null && imageUrl.isNullOrBlank()){
            throw IOException("No image is selected")
        }

        return uploadImage(localImageUri) ?: imageUrl!!
    }

    suspend fun getImageUrls(localImageUris: List<Uri?>?): List<String?> {
        if (localImageUris.isNullOrEmpty()){
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

    suspend fun <T, R> Iterable<T>.mapSuspend(transform: suspend (T) -> R): List<R>  =
        coroutineScope { map { t: T ->  async { transform(t) } }.map { it.await() } }


    suspend fun <T, R> Iterable<T>.mapIndexSuspend(transform: suspend (index: Int, T) -> R) =
        coroutineScope { mapIndexed { index: Int, t: T ->  async { transform(index, t) } }.map { it.await() } }
}