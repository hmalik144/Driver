package h_mal.appttude.com.driver.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.model.DriversLicenseObject
import h_mal.appttude.com.driver.utils.Coroutines.io

class DriverLicenseViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<DriversLicenseObject>(database, storage) {
    val uid = auth.getUid()!!

    override val databaseRef: DatabaseReference = database.getDriverLicenseRef(uid)
    override val storageRef: StorageReference = storage.driversLicenseStorageRef(uid)
    override val objectName: String = "drivers license"

    override fun getDataFromDatabase() = getDataClass<DriversLicenseObject>()

    override fun setDataInDatabase(data: DriversLicenseObject, localImageUri: Uri?) = io {
        doTryOperation("Failed to upload $objectName"){
            val imageUrl = getImageUrl(localImageUri, data.licenseImageString)
            data.licenseImageString = imageUrl
            postDataToDatabase(data)
        }
    }

}