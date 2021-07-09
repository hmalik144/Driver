package h_mal.appttude.com.driver.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.model.DriverProfileObject
import h_mal.appttude.com.driver.utils.Coroutines.io

class DriverProfileViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<DriverProfileObject>(database, storage) {
    val uid = auth.getUid()!!

    override val databaseRef: DatabaseReference = database.getDriverDetailsRef(uid)
    override val storageRef: StorageReference = storage.profileImageStorageRef(uid)
    override val objectName: String = "drivers profile"

    override fun getDataFromDatabase() = getDataClass<DriverProfileObject>()

    override fun setDataInDatabase(data: DriverProfileObject, localImageUri: Uri?) = io {
        doTryOperation("Failed to upload $objectName"){

            val imageUrl = getImageUrl(localImageUri, data.driverPic)
            data.driverPic = imageUrl

            postDataToDatabase(data)
        }
    }

}