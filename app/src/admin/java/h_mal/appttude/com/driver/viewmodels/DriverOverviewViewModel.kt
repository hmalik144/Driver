package h_mal.appttude.com.driver.viewmodels

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.objects.ApprovalsObject
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.getDataFromDatabaseRef
import kotlinx.coroutines.Job

class DriverOverviewViewModel(
    auth: FirebaseAuthentication,
    private val database: FirebaseDatabaseSource
) : DataSubmissionBaseViewModel<ApprovalsObject>(auth, database, null) {

    private var driverId: String? = null

    override val databaseRef: DatabaseReference = database.getApprovalsRef(driverId ?: "")
    override val storageRef: StorageReference? = null
    override val objectName: String = "Approvals"

    override fun getDataFromDatabase(): Job = retrieveDataFromDatabase<ApprovalsObject>()

    fun loadDriverApprovals(driverId: String?) {
        this.driverId = driverId
        io {
            doTryOperation("Failed to retrieve $objectName") {
                val data = driverId?.let {
                    database.getApprovalsRef(it).getDataFromDatabaseRef()
                } ?: ApprovalsObject()
                val mappedData = mapApprovalsForView(data)
                onSuccess(mappedData)
                return@doTryOperation
            }
        }
    }

    private fun mapApprovalsForView(data: ApprovalsObject): Map<String, Int> {
        return mutableMapOf<String, Int>().apply {
            put("Driver Profile", data.driver_details_approval)
            put("Drivers License", data.driver_license_approval)
            put("Private Hire License", data.private_hire_approval)
            put("Vehicle Profile", data.vehicle_details_approval)
            put("Insurance", data.insurance_details_approval)
            put("M.O.T", data.mot_details_approval)
            put("Log book", data.log_book_approval)
            put("Private Hire Vehicle License", data.ph_car_approval)
        }.toMap()
    }

}
