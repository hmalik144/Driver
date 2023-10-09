package h_mal.appttude.com.driver.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.PRIVATE_HIRE_SREF
import h_mal.appttude.com.driver.data.PRIVATE_HIRE_VEHICLE_SREF
import h_mal.appttude.com.driver.model.PrivateHireLicense
import h_mal.appttude.com.driver.model.PrivateHireVehicle
import h_mal.appttude.com.driver.utils.Coroutines.io
import kotlinx.coroutines.Job

class PrivateHireVehicleViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<PrivateHireVehicle>(auth, database, storage) {

    override val databaseRef: DatabaseReference = database.getPrivateHireVehicleRef(uid)
    override val storageRef: StorageReference = storage.privateHireVehicleStorageRef(uid)
    override val objectName: String = "private hire vehicle license"

    override fun getDataFromDatabase() = retrieveDataFromDatabase<PrivateHireVehicle>()

    override fun setDataInDatabase(data: PrivateHireVehicle, localImageUri: Uri?) = io {
        doTryOperation("Failed to upload $objectName") {
            val imageUrl = getImageUrl(localImageUri, data.phCarImageString)
            data.phCarImageString = imageUrl
            postDataToDatabase(data)
        }
    }
}