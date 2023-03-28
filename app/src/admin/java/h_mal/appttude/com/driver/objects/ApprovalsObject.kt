package h_mal.appttude.com.driver.admin.objects


class ApprovalsObject {
    var driver_details_approval: Int = 0
    var driver_license_approval: Int = 0
    var private_hire_approval: Int = 0
    var vehicle_details_approval: Int = 0
    var mot_details_approval: Int = 0
    var insurance_details_approval: Int = 0
    var log_book_approval: Int = 0
    var ph_car_approval: Int = 0

    constructor()
    constructor(
        driver_details_approval: Int,
        driver_license_approval: Int,
        private_hire_approval: Int,
        vehicle_details_approval: Int,
        mot_details_approval: Int,
        insurance_details_approval: Int,
        log_book_approval: Int,
        private_hire_vehicle_approval: Int
    ) {
        this.driver_details_approval = driver_details_approval
        this.driver_license_approval = driver_license_approval
        this.private_hire_approval = private_hire_approval
        this.vehicle_details_approval = vehicle_details_approval
        this.mot_details_approval = mot_details_approval
        this.insurance_details_approval = insurance_details_approval
        this.log_book_approval = log_book_approval
        this.ph_car_approval = private_hire_vehicle_approval
    }
}