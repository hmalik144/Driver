package h_mal.appttude.com.data

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

const val USER_CONST = "user"
const val PROFILE_ROLE = "role"
const val DRIVER_NUMBER = "driver_number"
const val USER_DETAILS = "user_details"
const val VEHICLE_PROFILE = "vehicle_profile"
const val DRIVER_PROFILE = "driver_profile"
const val APPROVALS = "approvalsObject"
const val DRIVER_DETAILS = "driver_details"
const val DRIVER_LICENSE = "driver_license"
const val PRIVATE_HIRE = "private_hire"
const val INSURANCE_DETAILS = "insurance_details"
const val LOG_BOOK = "log_book"
const val MOT = "mot_details"
const val PRIVATE_HIRE_VEHICLE = "private_hire_vehicle"
const val VEHICLE_DETAILS = "vehicle_details"
const val ARCHIVE = "archive"

class FirebaseDatabaseSource {
    private val database = FirebaseDatabase.getInstance()

    /**
     * Post object to the databse on reference
     *
     * @param ref - Database reference
     * @return T returns data posted
     */
    suspend fun <T : Any> postToDatabaseRed(ref: DatabaseReference, data: T): T {
        ref.setValue(data).await()
        return data
    }

    fun getUserRef(uid: String) = database.getReference(USER_CONST).child(uid)
    fun getUserDetailsRef(uid: String) = getUserRef(uid).child(USER_DETAILS)
    fun getVehicleRef(uid: String) = getUserRef(uid).child(VEHICLE_PROFILE)
    fun getDriverRef(uid: String) = getUserRef(uid).child(DRIVER_PROFILE)
    fun getApprovalsRef(uid: String) = getUserRef(uid).child(APPROVALS)
    fun getArchiveRef(uid: String) = getUserRef(uid).child(ARCHIVE)
    fun getUserRoleRef(uid: String) = getUserRef(uid).child(PROFILE_ROLE)
    fun getDriverNumberRef(uid: String) = getUserRef(uid).child(DRIVER_NUMBER)

    fun getDriverDetailsRef(uid: String) = getDriverRef(uid).child(DRIVER_DETAILS)
    fun getDriverLicenseRef(uid: String) = getDriverRef(uid).child(DRIVER_LICENSE)
    fun getPrivateHireRef(uid: String) = getDriverRef(uid).child(PRIVATE_HIRE)

    fun getInsuranceDetailsRef(uid: String) = getVehicleRef(uid).child(INSURANCE_DETAILS)
    fun getLogbookRef(uid: String) = getVehicleRef(uid).child(LOG_BOOK)
    fun getMotDetailsRef(uid: String) = getVehicleRef(uid).child(MOT)
    fun getPrivateHireVehicleRef(uid: String) = getVehicleRef(uid).child(PRIVATE_HIRE_VEHICLE)
    fun getVehicleDetailsRef(uid: String) = getVehicleRef(uid).child(VEHICLE_DETAILS)

    fun getDriverLicenseArchiveRef(uid: String) = getArchiveRef(uid).child(DRIVER_LICENSE)
    fun getArchiveInsuranceDetailsRef(uid: String) = getArchiveRef(uid).child(INSURANCE_DETAILS)
    fun getArchiveLogbookRef(uid: String) = getArchiveRef(uid).child(LOG_BOOK)
    fun getArchiveMotDetailsRef(uid: String) = getArchiveRef(uid).child(MOT)
    fun getArchivePrivateHireLicenseRef(uid: String) = getArchiveRef(uid).child(PRIVATE_HIRE)
    fun getArchivePrivateHireVehicleRef(uid: String) =
        getArchiveRef(uid).child(PRIVATE_HIRE_VEHICLE)

    fun getArchiveVehicleDetailsRef(uid: String) = getArchiveRef(uid).child(VEHICLE_DETAILS)
}