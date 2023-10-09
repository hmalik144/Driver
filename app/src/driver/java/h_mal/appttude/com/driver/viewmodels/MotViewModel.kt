package h_mal.appttude.com.driver.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.LOG_BOOK_SREF
import h_mal.appttude.com.driver.data.MOT
import h_mal.appttude.com.driver.data.MOT_SREF
import h_mal.appttude.com.driver.model.Logbook
import h_mal.appttude.com.driver.model.Mot
import h_mal.appttude.com.driver.utils.Coroutines.io
import kotlinx.coroutines.Job

class MotViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<Mot>(auth, database, storage) {

    override val databaseRef: DatabaseReference = database.getMotDetailsRef(uid)
    override val storageRef: StorageReference? = storage.motStorageRef(uid)
    override val objectName: String = "vehicle profile"

    override fun getDataFromDatabase() = retrieveDataFromDatabase<Mot>()

    override fun setDataInDatabase(data: Mot, localImageUri: Uri?) = io {
        doTryOperation("Failed to upload $objectName") {
            val imageUrl = getImageUrl(localImageUri, data.motImageString)
            data.motImageString = imageUrl
            postDataToDatabase(data)
        }
    }

}