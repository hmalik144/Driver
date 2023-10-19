package h_mal.appttude.com.driver.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.base.DataSubmissionViewModel2
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.MOT_SREF
import h_mal.appttude.com.driver.data.PRIVATE_HIRE_SREF
import h_mal.appttude.com.driver.data.Storage
import h_mal.appttude.com.driver.model.Mot
import h_mal.appttude.com.driver.model.PrivateHireLicense
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.DateUtils
import kotlinx.coroutines.Job
import org.joda.time.LocalDate

class PrivateHireLicenseViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionViewModel2<PrivateHireLicense>(auth, database, storage, Storage.DRIVERS_LICENSE) {

    override val databaseRef: DatabaseReference = database.getPrivateHireRef(uid)
    override val storageRef: StorageReference = storage.privateHireStorageRef(uid)

    override fun validateData(data: PrivateHireLicense): Boolean {
        data.phNumber.validateStringOrThrow("License number")
        if (DateUtils.parseDateStringIntoCalender(data.phExpiry!!).isBefore(LocalDate.now())) {
            onError("License expiry cannot be before today")
            return false
        }

        return true
    }

}