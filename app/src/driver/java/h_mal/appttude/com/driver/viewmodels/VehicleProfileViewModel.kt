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

class VehicleProfileViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource
) : DataSubmissionViewModel<VehicleProfile>(auth, database) {

    override val databaseRef: DatabaseReference = database.getVehicleDetailsRef(uid)

    override fun validateData(data: VehicleProfile): Boolean {
        data.colour.validateStringOrThrow("Vehicle colour")
        data.model.validateStringOrThrow("Vehicle model")
        data.reg.validateStringOrThrow("Vehicle registration plate")
        data.make.validateStringOrThrow("Vehicle make")
        data.keeperAddress.validateStringOrThrow("Keeper address")
        data.keeperName.validateStringOrThrow("Keeper name")
        data.keeperPostCode.validateStringOrThrow("Keeper post code")


        return true
    }


}