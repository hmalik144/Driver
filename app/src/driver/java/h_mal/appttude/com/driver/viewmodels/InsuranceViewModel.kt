package h_mal.appttude.com.driver.viewmodels

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionViewModel3
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.Storage
import h_mal.appttude.com.driver.model.Insurance
import h_mal.appttude.com.driver.utils.DateUtils
import org.joda.time.LocalDate

class InsuranceViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionViewModel3<Insurance>(auth, database, storage, Storage.INSURANCE) {

    override val databaseRef: DatabaseReference = database.getInsuranceDetailsRef(uid)
    override val storageRef: StorageReference = storage.insuranceStorageRef(uid)

    override fun validateData(data: Insurance): Boolean {
        data.insurerName.validateStringOrThrow("Insurer name")
        if (DateUtils.parseDateStringIntoCalender(data.expiryDate!!).isBefore(LocalDate.now())) {
            onError("Insurance expiry cannot be before today")
            return false
        }
        return true
    }

}