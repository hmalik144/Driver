package h_mal.appttude.com.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.data.FirebaseAuthentication
import h_mal.appttude.com.data.FirebaseDatabaseSource
import h_mal.appttude.com.data.FirebaseStorageSource
import h_mal.appttude.com.model.DriversLicense
import h_mal.appttude.com.utils.Coroutines.io

class DriverLicenseViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<DriversLicense>(auth, database, storage) {

    override val databaseRef: DatabaseReference = database.getDriverLicenseRef(uid)
    override val storageRef: StorageReference = storage.driversLicenseStorageRef(uid)
    override val objectName: String = "drivers license"

    override fun getDataFromDatabase() = getDataClass<DriversLicense>()

    override fun setDataInDatabase(data: DriversLicense, localImageUri: Uri?) = io {
        doTryOperation("Failed to upload $objectName") {
            val imageUrl = getImageUrl(localImageUri, data.licenseImageString)
            data.licenseImageString = imageUrl
            postDataToDatabase(data)
        }
    }

}