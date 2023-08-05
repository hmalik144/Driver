package h_mal.appttude.com.driver.objects

import h_mal.appttude.com.driver.objects.wholeObject.DriverProfile
import h_mal.appttude.com.driver.objects.wholeObject.VehicleProfile


data class WholeDriverObject(
    var driver_profile: DriverProfile? = DriverProfile(),
    var role: String? = "",
    var archive: ArchiveObject? = ArchiveObject(),
    var user_details: UserObject? = UserObject(),
    var vehicle_profile: VehicleProfile? = VehicleProfile(),
    var approvalsObject: ApprovalsObject? = ApprovalsObject(),
    var driver_number: String? = "",
)