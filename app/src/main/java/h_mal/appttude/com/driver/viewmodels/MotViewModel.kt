package h_mal.appttude.com.driver.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.model.MotObject
import h_mal.appttude.com.driver.utils.Coroutines.io

class MotViewModel (
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<MotObject>(database, storage) {
    val uid = auth.getUid()!!

    override val databaseRef: DatabaseReference = database.getMotDetailsRef(uid)
    override val storageRef: StorageReference? = storage.motStorageRef(uid)
    override val objectName: String = "vehicle profile"

    override fun getDataFromDatabase() = getDataClass<MotObject>()

    override fun setDataInDatabase(data: MotObject, localImageUri: Uri?) = io {
        doTryOperation("Failed to upload $objectName"){
            val imageUrl = getImageUrl(localImageUri, data.motImageString)
            data.motImageString = imageUrl
            postDataToDatabase(data)
        }
    }

}