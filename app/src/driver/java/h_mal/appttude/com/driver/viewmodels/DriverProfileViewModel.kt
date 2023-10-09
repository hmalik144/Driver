package h_mal.appttude.com.driver.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.data.DRIVER_PROFILE
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.PROFILE_SREF
import h_mal.appttude.com.driver.model.DriverProfile
import h_mal.appttude.com.driver.utils.Coroutines.io
import kotlinx.coroutines.Job

class DriverProfileViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<DriverProfile>(auth, database, storage) {

    override val databaseRef: DatabaseReference = database.getDriverDetailsRef(uid)
    override val storageRef: StorageReference = storage.profileImageStorageRef(uid)
    override val objectName: String = "drivers profile"
    override fun getDataFromDatabase() = retrieveDataFromDatabase<DriverProfile>()

    override fun setDataInDatabase(data: DriverProfile, localImageUri: Uri?) = io {
        doTryOperation("Failed to upload $objectName") {

            val imageUrl = getImageUrl(localImageUri, data.driverPic)
            data.driverPic = imageUrl
            postDataToDatabase(data)
        }
    }

}