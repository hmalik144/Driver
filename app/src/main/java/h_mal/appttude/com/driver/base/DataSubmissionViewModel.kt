package h_mal.appttude.com.driver.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import h_mal.appttude.com.driver.data.DataState
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseCompletion
import h_mal.appttude.com.driver.data.FirebaseDataLiveData
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.GenericsHelper.getGenericClassAt
import h_mal.appttude.com.driver.utils.toLiveData
import java.io.IOException


abstract class DataSubmissionViewModel<T : Document>(
    auth: FirebaseAuthentication,
    private val database: FirebaseDatabaseSource
) : BaseViewModel() {
    val stateLiveData = auth.userStateListener()
    val uid: String = auth.getUid() ?: throw IOException("User is not logged in")

    abstract val databaseRef: DatabaseReference

    private val refLiveData: LiveData<DataState> by lazy { FirebaseDataLiveData<T>(databaseRef, getGenericClassAt<T>(0).java) }
    @Suppress("UNCHECKED_CAST")
    private val observer = Observer<DataState> {
        when (it) {
            is DataState.HasData<*> -> {
                if (it.data is Document) setData(it.data as T)
            }
            is DataState.HasError -> onError(it.error.message)
        }
    }

    private val valueListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            snapshot.getValue(getGenericClassAt<T>(0).javaObjectType)
        }

        override fun onCancelled(error: DatabaseError) {

        }
    }

    open fun setData(data: T) {
        onSuccess(data)
    }

    fun init() {
//        databaseRef.addValueEventListener()
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
            doTryOperation("Failed to submit document") {
                if (!validateData(data)) return@doTryOperation

                val postObject = database.postToDatabaseRed(databaseRef, data)
                onSuccess(postObject)
            }
        }
    }

    fun String?.validateStringOrThrow(fieldName: String) {
        if (isNullOrEmpty()) {
            throw IOException("$fieldName cannot be empty")
        }
    }

}