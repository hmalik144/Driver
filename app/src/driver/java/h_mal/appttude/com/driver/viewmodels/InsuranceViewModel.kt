package h_mal.appttude.com.driver.viewmodels

import android.net.Uri
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.INSURANCE_SREF
import h_mal.appttude.com.driver.model.Insurance
import h_mal.appttude.com.driver.utils.Coroutines.io
import kotlinx.coroutines.Job

class InsuranceViewModel(
    auth: FirebaseAuthentication,
    database: FirebaseDatabaseSource,
    storage: FirebaseStorageSource
) : DataSubmissionBaseViewModel<Insurance>(auth, database, storage) {

    override val databaseRef: DatabaseReference = database.getInsuranceDetailsRef(uid)
    override val storageRef: StorageReference = storage.insuranceStorageRef(uid)
    override val objectName: String = "insurance"

    override fun getDataFromDatabase() = retrieveDataFromDatabase<Insurance>()

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