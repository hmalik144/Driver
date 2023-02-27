package h_mal.appttude.com.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.data.FirebaseAuthentication
import h_mal.appttude.com.data.FirebaseDatabaseSource
import h_mal.appttude.com.data.FirebaseStorageSource
import h_mal.appttude.com.model.PrivateHireObject
import h_mal.appttude.com.utils.Coroutines.io

class PrivateHireLicenseViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<PrivateHireObject>(auth, database, storage) {

    override val databaseRef: DatabaseReference = database.getPrivateHireRef(uid)
    override val storageRef: StorageReference = storage.privateHireStorageRef(uid)
    override val objectName: String = "private hire license"

    override fun getDataFromDatabase() = getDataClass<PrivateHireObject>()

    override fun setDataInDatabase(data: PrivateHireObject, localImageUri: Uri?) = io {
        doTryOperation("Failed to upload private hire license"){
            val imageUrl = getImageUrl(localImageUri, data.phImageString)
            val driverLicense = PrivateHireObject(
                phExpiry = data.phExpiry,
                phNumber = data.phNumber,
                phImageString = imageUrl
            )

            postDataToDatabase(driverLicense)
        }
    }

}