package h_mal.appttude.com.driver.viewmodels

import h_mal.appttude.com.driver.base.BaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.getDataFromDatabaseRef

class MainViewModel(
    private val firebaseAuth: FirebaseAuthentication,
    private val firebaseDatabase: FirebaseDatabaseSource
) : BaseViewModel(){

    val uid = firebaseAuth.getUid()!!

    fun getRole() = io {
            doTryOperation("failed to retrieve data") {
                val ref = firebaseDatabase.getUserRoleRef(uid)
                val role = ref.getDataFromDatabaseRef<String>()
                role?.apply { onSuccess(this) } ?: onError("No role found")
//
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