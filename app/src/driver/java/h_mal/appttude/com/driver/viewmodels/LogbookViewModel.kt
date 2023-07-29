package h_mal.appttude.com.driver.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.model.Logbook
import h_mal.appttude.com.driver.utils.Coroutines.io

class LogbookViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<Logbook>(auth, database, storage) {

    override val databaseRef: DatabaseReference = database.getLogbookRef(uid)
    override val storageRef: StorageReference = storage.logBookStorageRef(uid)
    override val objectName: String = "Log book"

    override fun getDataFromDatabase() = retrieveDataFromDatabase<Logbook>()

    override fun setDataInDatabase(data: Logbook, localImageUri: Uri?) = io {
        doTryOperation("Failed to upload $objectName") {
            val imageUrl = getImageUrl(localImageUri, data.photoString)
            data.photoString = imageUrl
            postDataToDatabase(data)
        }
    }

}