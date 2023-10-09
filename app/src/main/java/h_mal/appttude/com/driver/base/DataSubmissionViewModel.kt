package h_mal.appttude.com.driver.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.firebase.database.DatabaseReference
import h_mal.appttude.com.driver.data.DataState
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseCompletion
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.toLiveData
import java.io.IOException


abstract class DataSubmissionViewModel<T : Any>(
    auth: FirebaseAuthentication,
    private val database: FirebaseDatabaseSource
) : BaseViewModel() {
    val stateLiveData = auth.userStateListener()
    val uid: String = auth.getUid() ?: throw IOException("User is not logged in")

    abstract val databaseRef: DatabaseReference

    private val refLiveData: LiveData<DataState> by lazy { databaseRef.toLiveData<T>() }
    private val observer = Observer<DataState> {
        when (it) {
            is DataState.HasData<*> -> onSuccess(it.data)
            is DataState.HasError -> onError(it.error.message)
        }
    }

    init {
        refLiveData.observeForever(observer)
    }

    override fun onCleared() {
        super.onCleared()
        refLiveData.removeObserver(observer)
    }

    // Validate the data before posting to database
    abstract fun validateData(data: T): Boolean

    @Suppress("UNCHECKED_CAST")
    fun getDataFromDatabase(): T? {
        return when (val data = refLiveData.value) {
            is DataState.HasData<*> -> data.data as T
            else -> null
        }
    }

    open fun postDataToDatabase(data: T) {
        io {
            if (!validateData(data)) return@io
            if (getDataFromDatabase() == data) return@io
            doTryOperation("Failed to submit document") {
                val postObject = database.postToDatabaseRed(databaseRef, data)
                onSuccess(postObject)
            }
        }
    }

}