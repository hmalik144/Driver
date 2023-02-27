package h_mal.appttude.com.objects

import h_mal.appttude.com.model.*
import java.util.*

class ArchiveObject {
    var driver_license: HashMap<String, DriversLicenseObject>? = null
    var private_hire: HashMap<String, PrivateHireObject>? = null
    var vehicle_details: HashMap<String, VehicleProfileObject>? = null
    var insurance_details: HashMap<String, InsuranceObject>? = null
    var mot_details: HashMap<String, MotObject>? = null
    var log_book: HashMap<String, LogbookObject>? = null
    var ph_car: HashMap<String, PrivateHireVehicleObject>? = null

    constructor()
    constructor(
        driver_license: HashMap<String, DriversLicenseObject>?,
        private_hire: HashMap<String, PrivateHireObject>?,
        vehicle_details: HashMap<String, VehicleProfileObject>?,
        insurance_details: HashMap<String, InsuranceObject>?,
        mot_details: HashMap<String, MotObject>?,
        log_book: HashMap<String, LogbookObject>?,
        private_hire_vehicle: HashMap<String, PrivateHireVehicleObject>?
    ) {
        this.driver_license = driver_license
        this.private_hire = private_hire
        this.vehicle_details = vehicle_details
        this.insurance_details = insurance_details
        this.mot_details = mot_details
        this.log_book = log_book
        this.ph_car = private_hire_vehicle
    }
}