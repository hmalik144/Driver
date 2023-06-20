package h_mal.appttude.com.driver.viewmodels

import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.model.ApprovalStatus
import h_mal.appttude.com.driver.objects.ApprovalsObject
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.getDataFromDatabaseRef
import kotlinx.coroutines.Job
import java.io.IOException

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

    private fun mapApprovalsForView(data: ApprovalsObject): List<Pair<String, ApprovalStatus>> {
        val list = mutableListOf<Pair<String, ApprovalStatus>>()
         return list.apply {
            add(0, Pair("Driver Profile", getApprovalStatusByScore(data.driver_details_approval)))
            add(1, Pair("Drivers License", getApprovalStatusByScore(data.driver_license_approval)))
            add(2, Pair("Private Hire License", getApprovalStatusByScore(data.private_hire_approval)))
            add(3, Pair("Vehicle Profile", getApprovalStatusByScore(data.vehicle_details_approval)))
            add(4, Pair("Insurance", getApprovalStatusByScore(data.insurance_details_approval)))
            add(5, Pair("M.O.T", getApprovalStatusByScore(data.mot_details_approval)))
            add(6, Pair("Log book", getApprovalStatusByScore(data.log_book_approval)))
            add(7, Pair("Private Hire Vehicle License", getApprovalStatusByScore(data.ph_car_approval)))
        }
    }

    private fun getApprovalStatusByScore(score: Int): ApprovalStatus {
        if (score == 0) return ApprovalStatus.NOT_SUBMITTED
        return ApprovalStatus.getByScore(score) ?: throw IOException("No approval for score $score")
    }

}
