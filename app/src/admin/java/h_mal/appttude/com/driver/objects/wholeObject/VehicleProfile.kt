package h_mal.appttude.com.driver.admin.objects.wholeObject

import h_mal.appttude.com.driver.model.Insurance
import h_mal.appttude.com.driver.model.Logbook
import h_mal.appttude.com.driver.model.PrivateHireVehicle
import h_mal.appttude.com.driver.model.Mot
import h_mal.appttude.com.driver.model.VehicleProfile


class VehicleProfile {
    var insurance_details: Insurance? = null
    var log_book: Logbook? = null
    var mot_details: Mot? = null
    var vehicle_details: VehicleProfile? = null
    var privateHireVehicle: PrivateHireVehicle? = null

    constructor()
    constructor(
        insurance_details: Insurance?,
        log_book: Logbook?,
        mot_details: Mot?,
        vehicle_details: VehicleProfile?,
        private_hire_vehicle: PrivateHireVehicle?
    ) {
        this.insurance_details = insurance_details
        this.log_book = log_book
        this.mot_details = mot_details
        this.vehicle_details = vehicle_details
        this.privateHireVehicle = private_hire_vehicle
    }
}