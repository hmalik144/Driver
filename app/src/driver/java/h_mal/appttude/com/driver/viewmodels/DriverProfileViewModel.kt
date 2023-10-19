package h_mal.appttude.com.driver.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.base.DataSubmissionViewModel2
import h_mal.appttude.com.driver.data.DRIVER_PROFILE
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.PROFILE_SREF
import h_mal.appttude.com.driver.data.Storage
import h_mal.appttude.com.driver.model.DriverProfile
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.DateUtils
import kotlinx.coroutines.Job
import org.joda.time.LocalDate

class DriverProfileViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionViewModel2<DriverProfile>(auth, database, storage, Storage.PROFILE) {

    override val databaseRef: DatabaseReference = database.getDriverDetailsRef(uid)
    override val storageRef: StorageReference = storage.profileImageStorageRef(uid)

    override fun validateData(data: DriverProfile): Boolean {
        data.ni.validateStringOrThrow("National Insurance number")
        data.address.validateStringOrThrow("Address")
        data.postcode.validateStringOrThrow("Postcode")
        data.forenames.validateStringOrThrow("Name")
        data.dob
        if (DateUtils.parseDateStringIntoCalender(data.dob!!).isAfter(LocalDate.now().minusYears(17))) {
            onError("Driver cannot be under 17")
            return false
        }
        return true
    }

}