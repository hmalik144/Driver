package h_mal.appttude.com.driver.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.model.PrivateHireLicense
import h_mal.appttude.com.driver.utils.Coroutines.io

class PrivateHireLicenseViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<PrivateHireLicense>(auth, database, storage) {

    override val databaseRef: DatabaseReference = database.getPrivateHireRef(uid)
    override val storageRef: StorageReference = storage.privateHireStorageRef(uid)
    override val objectName: String = "private hire license"

    override fun getDataFromDatabase() = retrieveDataFromDatabase<PrivateHireLicense>()

    override fun setDataInDatabase(data: PrivateHireLicense, localImageUri: Uri?) = io {
        doTryOperation("Failed to upload private hire license") {
            val imageUrl = getImageUrl(localImageUri, data.phImageString)
            val driverLicense = PrivateHireLicense(
                phExpiry = data.phExpiry,
                phNumber = data.phNumber,
                phImageString = imageUrl
            )

            postDataToDatabase(driverLicense)
        }
    }

}