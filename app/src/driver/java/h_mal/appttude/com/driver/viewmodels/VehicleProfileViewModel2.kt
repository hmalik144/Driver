package h_mal.appttude.com.driver.viewmodels

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.base.DataSubmissionViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.model.VehicleProfile
import h_mal.appttude.com.driver.utils.Coroutines.io
import java.io.IOException

class VehicleProfileViewModel2(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource
) : DataSubmissionViewModel<VehicleProfile>(auth, database) {

    override val databaseRef: DatabaseReference = database.getVehicleDetailsRef(uid)

    override fun validateData(data: VehicleProfile): Boolean {
        if (data.model.isNullOrEmpty()) {
            throw IOException("Vehicle model cannot be empty")
        }
        if (data.reg.isNullOrEmpty()) {
            throw IOException("Vehicle registration cannot be empty")
        }
        if (data.make.isNullOrEmpty()) {
            throw IOException("Vehicle make cannot be empty")
        }
        if (data.colour.isNullOrEmpty()) {
            throw IOException("Vehicle colour cannot be empty")
        }
        if (data.keeperName.isNullOrEmpty()) {
            throw IOException("Keepers name cannot be empty")
        }
        if (data.keeperAddress.isNullOrEmpty()) {
            throw IOException("Keepers address cannot be empty")
        }
        if (data.keeperPostCode.isNullOrEmpty()) {
            throw IOException("Keepers post code cannot be empty")
        }
        return true
    }

}