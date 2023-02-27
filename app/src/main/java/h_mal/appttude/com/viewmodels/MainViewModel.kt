package h_mal.appttude.com.viewmodels

import h_mal.appttude.com.base.BaseViewModel
import h_mal.appttude.com.data.FirebaseAuthentication
import h_mal.appttude.com.data.FirebaseDatabaseSource
import h_mal.appttude.com.utils.Coroutines.io
import h_mal.appttude.com.utils.getDataFromDatabaseRef

class MainViewModel(
    private val firebaseAuth: FirebaseAuthentication,
    private val firebaseDatabase: FirebaseDatabaseSource
) : BaseViewModel(){

    fun getRole() = io {
            doTryOperation("failed to retrieve data") {
                val uid = firebaseAuth.getUid() ?: return@doTryOperation
                val ref = firebaseDatabase.getUserRoleRef(uid)
                val role = ref.getDataFromDatabaseRef<String>()
                role?.apply { onSuccess(this) } ?: onError("No role found")
            }
        }


    fun getUserDetails(){
        firebaseAuth.getUser()?.let {
            onSuccess(it)
        }
    }

    fun logOut(){
        firebaseAuth.logOut()
    }
}