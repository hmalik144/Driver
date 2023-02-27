package h_mal.appttude.com.viewmodels

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.data.FirebaseAuthentication
import h_mal.appttude.com.data.FirebaseDatabaseSource
import h_mal.appttude.com.data.FirebaseStorageSource
import h_mal.appttude.com.model.VehicleProfileObject
import h_mal.appttude.com.utils.Coroutines.io

class VehicleProfileViewModel (
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<VehicleProfileObject>(auth, database, storage) {

    override val databaseRef: DatabaseReference = database.getVehicleDetailsRef(uid)
    override val storageRef: StorageReference? = null
    override val objectName: String = "vehicle profile"

    override fun getDataFromDatabase() = getDataClass<VehicleProfileObject>()

    override fun setDataInDatabase(data: VehicleProfileObject) {
        io {
            doTryOperation("Failed to upload $objectName"){
                postDataToDatabase(data)
            }
        }
    }


}