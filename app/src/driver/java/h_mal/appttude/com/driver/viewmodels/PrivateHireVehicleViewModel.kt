package h_mal.appttude.com.driver.viewmodels

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionViewModel2
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.Storage
import h_mal.appttude.com.driver.model.PrivateHireVehicle
import h_mal.appttude.com.driver.utils.DateUtils
import org.joda.time.LocalDate

class PrivateHireVehicleViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionViewModel2<PrivateHireVehicle>(
    auth,
    database,
    storage,
    Storage.PRIVATE_HIRE_VEHICLE
) {

    override val databaseRef: DatabaseReference = database.getPrivateHireVehicleRef(uid)
    override val storageRef: StorageReference = storage.privateHireVehicleStorageRef(uid)

    override fun validateData(data: PrivateHireVehicle): Boolean {
        data.phCarNumber.validateStringOrThrow("License number")
        if (DateUtils.parseDateStringIntoCalender(data.phCarExpiry!!).isBefore(LocalDate.now())) {
            onError("License expiry cannot be before today")
            return false
        }

        return true
    }
}