package h_mal.appttude.com.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.data.FirebaseAuthentication
import h_mal.appttude.com.data.FirebaseDatabaseSource
import h_mal.appttude.com.data.FirebaseStorageSource
import h_mal.appttude.com.model.DriverProfile
import h_mal.appttude.com.utils.Coroutines.io

class DriverProfileViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<DriverProfile>(auth, database, storage) {

    override val databaseRef: DatabaseReference = database.getDriverDetailsRef(uid)
    override val storageRef: StorageReference = storage.profileImageStorageRef(uid)
    override val objectName: String = "drivers profile"

    override fun getDataFromDatabase() = getDataClass<DriverProfile>()

    override fun setDataInDatabase(data: DriverProfile, localImageUri: Uri?) = io {
        doTryOperation("Failed to upload $objectName") {

            val imageUrl = getImageUrl(localImageUri, data.driverPic)
            data.driverPic = imageUrl

            postDataToDatabase(data)
        }
    }

}