package h_mal.appttude.com.driver.viewmodels

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.model.VehicleProfile
import h_mal.appttude.com.driver.utils.Coroutines.io

class VehicleProfileViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<VehicleProfile>(auth, database, storage) {

    override val databaseRef: DatabaseReference = database.getVehicleDetailsRef(uid)
    override val storageRef: StorageReference? = null
    override val objectName: String = "vehicle profile"

    override fun getDataFromDatabase() = retrieveDataFromDatabase<VehicleProfile>()

    override fun setDataInDatabase(data: VehicleProfile) {
        io {
            doTryOperation("Failed to upload $objectName") {
                postDataToDatabase(data)
            }
        }
    }


}