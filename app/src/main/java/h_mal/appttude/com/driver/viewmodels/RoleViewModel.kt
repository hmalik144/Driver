package h_mal.appttude.com.driver.viewmodels

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.utils.Coroutines.io

class RoleViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<String>(auth, database, storage) {

    override val databaseRef: DatabaseReference = database.getUserRoleRef(uid)
    override val storageRef: StorageReference? = null
    override val objectName: String = "role"

    override fun getDataFromDatabase() = retrieveDataFromDatabase<String>()

    override fun setDataInDatabase(data: String) {
        io {
            doTryOperation("Failed to upload $objectName") {
                postDataToDatabase(data)
            }
        }
    }

}