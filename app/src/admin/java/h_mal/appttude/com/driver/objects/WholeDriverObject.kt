package h_mal.appttude.com.driver.admin.objects

import h_mal.appttude.com.driver.admin.objects.wholeObject.DriverProfile
import h_mal.appttude.com.driver.admin.objects.wholeObject.VehicleProfile


class WholeDriverObject {
    var driver_profile: DriverProfile? = null
    var role: String? = null
    var archive: ArchiveObject? = null
    var user_details: UserObject? = null
    var vehicle_profile: VehicleProfile? = null
    var approvalsObject: ApprovalsObject? = null
    var driver_number: String? = null

    constructor(
        driver_profile: DriverProfile?,
        role: String?,
        archive: ArchiveObject?,
        user_details: UserObject?,
        vehicle_profile: VehicleProfile?,
        approvalsObject: ApprovalsObject?,
        driver_number: String?
    ) {
        this.driver_profile = driver_profile
        this.role = role
        this.archive = archive
        this.user_details = user_details
        this.vehicle_profile = vehicle_profile
        this.approvalsObject = approvalsObject
        this.driver_number = driver_number
    }

    constructor()
}