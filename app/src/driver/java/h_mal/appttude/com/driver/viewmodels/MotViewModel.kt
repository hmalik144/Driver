package h_mal.appttude.com.driver.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.base.DataSubmissionViewModel2
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.LOG_BOOK_SREF
import h_mal.appttude.com.driver.data.MOT
import h_mal.appttude.com.driver.data.MOT_SREF
import h_mal.appttude.com.driver.data.Storage
import h_mal.appttude.com.driver.model.Logbook
import h_mal.appttude.com.driver.model.Mot
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.DateUtils
import kotlinx.coroutines.Job
import org.joda.time.LocalDate

class MotViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionViewModel2<Mot>(auth, database, storage, Storage.MOT) {

    override val databaseRef: DatabaseReference = database.getMotDetailsRef(uid)
    override val storageRef: StorageReference = storage.motStorageRef(uid)

    override fun validateData(data: Mot): Boolean {
        if (DateUtils.parseDateStringIntoCalender(data.motExpiry!!).isBefore(LocalDate.now())) {
            onError("MOT expiry cannot be before today")
            return false
        }
        return true
    }

}