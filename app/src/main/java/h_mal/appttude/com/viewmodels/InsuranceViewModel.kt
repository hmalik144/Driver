package h_mal.appttude.com.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.data.FirebaseAuthentication
import h_mal.appttude.com.data.FirebaseDatabaseSource
import h_mal.appttude.com.data.FirebaseStorageSource
import h_mal.appttude.com.model.Insurance
import h_mal.appttude.com.utils.Coroutines.io

class InsuranceViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<Insurance>(auth, database, storage) {

    override val databaseRef: DatabaseReference = database.getInsuranceDetailsRef(uid)
    override val storageRef: StorageReference = storage.insuranceStorageRef(uid)
    override val objectName: String = "insurance"

    override fun getDataFromDatabase() = getDataClass<Insurance>()

    override fun setDataInDatabase(data: Insurance, localImageUris: List<Uri?>?) = io {
        doTryOperation("Failed to upload $objectName") {
            val imageUrls = if (!localImageUris.isNullOrEmpty()) {
                getImageUrls(localImageUris).toMutableList()
            } else {
                data.photoStrings
            }
            if (imageUrls.isNullOrEmpty()) {
                onError("no images selected")
                return@doTryOperation
            }

            data.photoStrings = imageUrls
            postDataToDatabase(data)
        }
    }
}