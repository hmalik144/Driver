package h_mal.appttude.com.driver.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.Objects.PrivateHireVehicleObject
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.utils.Coroutines.io

class PrivateHireVehicleViewModel  (
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<PrivateHireVehicleObject>(database, storage) {
    val uid = auth.getUid()!!

    override val databaseRef: DatabaseReference = database.getPrivateHireVehicleRef(uid)
    override val storageRef: StorageReference? = storage.privateHireVehicleStorageRef(uid)
    override val objectName: String = "private hire vehicle license"

    override fun getDataFromDatabase() = getDataClass<PrivateHireVehicleObject>()

    override fun setDataInDatabase(data: PrivateHireVehicleObject, localImageUri: Uri?) = io {
        doTryOperation("Failed to upload $objectName") {
            val imageUrl = getImageUrl(localImageUri, data.phCarImageString)
            data.phCarImageString = imageUrl
            postDataToDatabase(data)
        }
    }
}