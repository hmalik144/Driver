package h_mal.appttude.com.driver.viewmodels

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionViewModel2
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.Storage
import h_mal.appttude.com.driver.model.Logbook

class LogbookViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionViewModel2<Logbook>(auth, database, storage, Storage.LOG_BOOK) {

    override val databaseRef: DatabaseReference = database.getLogbookRef(uid)
    override val storageRef: StorageReference = storage.logBookStorageRef(uid)

    override fun validateData(data: Logbook): Boolean {
        data.v5cnumber.validateStringOrThrow("V5C number")
        return true
    }

}