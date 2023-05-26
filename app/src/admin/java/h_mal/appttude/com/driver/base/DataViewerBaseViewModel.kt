package h_mal.appttude.com.driver.base

import com.google.firebase.database.DatabaseReference
import h_mal.appttude.com.driver.data.FirebaseCompletion
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.GenericsHelper.getGenericClassAt
import h_mal.appttude.com.driver.utils.getDataFromDatabaseRef
import h_mal.appttude.com.driver.utils.isNotNull

abstract class DataViewerBaseViewModel<T : Any> : BaseViewModel() {
    var uid: String? = null

    abstract fun getDatabaseRef(uid: String): DatabaseReference

    fun initViewModel(uid: String) {
        this.uid = uid
        retrieveData(uid)
    }

    fun fetchData() {
        @Suppress("IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")
        uid.isNotNull {
            retrieveData(it)
            return
        }

        onError("Failed to retrieve data for user")
    }

    private fun retrieveData(uid: String) {
        val clazz = getGenericClassAt<T>(0)
        io {
            doTryOperation("Failed to retrieve ${clazz.simpleName}") {
                val data = getDatabaseRef(uid).getDataFromDatabaseRef(clazz.java)
                onSuccess(data ?: FirebaseCompletion.Default)
            }
        }
    }

}