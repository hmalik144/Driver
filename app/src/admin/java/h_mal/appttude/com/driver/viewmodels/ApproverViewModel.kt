package h_mal.appttude.com.driver.viewmodels

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseViewModel
import h_mal.appttude.com.driver.data.FirebaseCompletion
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.USER_CONST
import h_mal.appttude.com.driver.model.ApprovalStatus
import h_mal.appttude.com.driver.objects.ApprovalsObject
import h_mal.appttude.com.driver.ui.driverprofile.DriverLicenseFragment
import h_mal.appttude.com.driver.ui.driverprofile.DriverProfileFragment
import h_mal.appttude.com.driver.ui.driverprofile.PrivateHireLicenseFragment
import h_mal.appttude.com.driver.ui.vehicleprofile.*
import h_mal.appttude.com.driver.utils.Coroutines.io
import h_mal.appttude.com.driver.utils.FRAGMENT
import h_mal.appttude.com.driver.utils.getDataFromDatabaseRef

class ApproverViewModel(
    private val resources: Resources,
    private val database: FirebaseDatabaseSource
) : BaseViewModel() {

    private lateinit var name: String
    private lateinit var docRef: DatabaseReference

    private var score: ApprovalStatus? = null

    fun init(args: Bundle) {
        // Retried uid & fragment class name from args
        val uid = args.getString(USER_CONST) ?: throw NullPointerException("No user Id was passed")
        name = args.getString(FRAGMENT)
            ?: throw NullPointerException("No fragment name argument passed")
        // Define a document name based on fragment class name
        val documentName = when (name) {
            resources.getString(R.string.driver_profile) -> ApprovalsObject::driver_details_approval.name
            resources.getString(R.string.drivers_license) -> ApprovalsObject::driver_license_approval.name
            resources.getString(R.string.private_hire_license) -> ApprovalsObject::private_hire_approval.name
            resources.getString(R.string.vehicle_profile) -> ApprovalsObject::vehicle_details_approval.name
            resources.getString(R.string.insurance) -> ApprovalsObject::insurance_details_approval.name
            resources.getString(R.string.m_o_t) -> ApprovalsObject::mot_details_approval.name
            resources.getString(R.string.log_book) -> ApprovalsObject::log_book_approval.name
            resources.getString(R.string.private_hire_vehicle_license) -> ApprovalsObject::ph_car_approval.name
            else -> {
                throw StringIndexOutOfBoundsException("No resource for $name")
            }
        }

        docRef = database.getDocumentApprovalRef(uid, documentName)
        io {
            doTryOperation("") {
                val data = docRef.getDataFromDatabaseRef<Int>()
                score = data?.let { ApprovalStatus.getByScore(it) } ?: ApprovalStatus.NOT_SUBMITTED
                onSuccess(FirebaseCompletion.Default)
            }
        }
    }

    fun getFragmentClass(): Class<out Fragment> {
        return when (name) {
            resources.getString(R.string.driver_profile) -> DriverProfileFragment::class.java
            resources.getString(R.string.drivers_license) -> DriverLicenseFragment::class.java
            resources.getString(R.string.private_hire_license) -> PrivateHireLicenseFragment::class.java
            resources.getString(R.string.vehicle_profile) -> VehicleProfileFragment::class.java
            resources.getString(R.string.insurance) -> InsuranceFragment::class.java
            resources.getString(R.string.m_o_t) -> MotFragment::class.java
            resources.getString(R.string.log_book) -> LogbookFragment::class.java
            resources.getString(R.string.private_hire_vehicle_license) -> PrivateHireVehicleFragment::class.java
            else -> {
                throw StringIndexOutOfBoundsException("No resource for $name")
            }
        }
    }

    fun approveDocument() {
        updateDocument(ApprovalStatus.APPROVED)
    }

    fun declineDocument() {
        updateDocument(ApprovalStatus.DENIED)
    }

    private fun updateDocument(approval: ApprovalStatus) {
        if (approval == score) {
            val result = if (approval == ApprovalStatus.APPROVED) "approved" else "declined"
            onError("Document already $result")
            return
        }

        io {
            doTryOperation("Failed to decline document") {
                database.postToDatabaseRed(docRef, approval.score)
                onSuccess(approval)
            }
        }
    }
}