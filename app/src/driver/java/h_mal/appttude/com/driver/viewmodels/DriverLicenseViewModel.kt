package h_mal.appttude.com.driver.viewmodels

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionViewModel2
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.Storage
import h_mal.appttude.com.driver.model.DriversLicense
import h_mal.appttude.com.driver.utils.DateUtils.parseDateStringIntoCalender
import org.joda.time.LocalDate

class DriverLicenseViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionViewModel2<DriversLicense>(auth, database, storage, Storage.DRIVERS_LICENSE) {

    override val databaseRef: DatabaseReference = database.getDriverLicenseRef(uid)
    override val storageRef: StorageReference = storage.driversLicenseStorageRef(uid)

    override fun validateData(data: DriversLicense): Boolean {
        data.licenseNumber.validateStringOrThrow("License number")
        if (parseDateStringIntoCalender(data.licenseExpiry!!).isBefore(LocalDate.now())) {
            onError("License expiry cannot be before today")
            return false
        }

        return true
    }

}