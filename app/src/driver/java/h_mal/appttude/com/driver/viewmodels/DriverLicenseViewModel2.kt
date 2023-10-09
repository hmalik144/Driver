package h_mal.appttude.com.driver.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.base.DataSubmissionViewModel2
import h_mal.appttude.com.driver.data.DRIVERS_LICENSE_SREF
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.Storage
import h_mal.appttude.com.driver.model.DriversLicense
import h_mal.appttude.com.driver.utils.Coroutines.io
import kotlinx.coroutines.Job

class DriverLicenseViewModel2(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionViewModel2<DriversLicense>(auth, database, storage) {

    override val databaseRef: DatabaseReference = database.getDriverLicenseRef(uid)
    override val storageRef: StorageReference = storage.driversLicenseStorageRef(uid)

    override fun validateData(data: DriversLicense): Boolean {
        // TODO Not yet implemented
        return true
    }
    override fun setDataAfterUpload(data: DriversLicense, filename: String): DriversLicense {
        return data.apply {
            licenseImageString = filename
        }
    }

    fun postDataToDatabase(imageUri: Uri, data: DriversLicense) {
        postDataToDatabase(imageUri, data, Storage.DRIVERS_LICENSE)
    }

}