package h_mal.appttude.com.driver.admin.objects

import h_mal.appttude.com.driver.model.*
import java.util.*

class ArchiveObject {
    var driver_license: HashMap<String, DriversLicense>? = null
    var private_hire: HashMap<String, PrivateHireLicense>? = null
    var vehicle_details: HashMap<String, VehicleProfile>? = null
    var insurance_details: HashMap<String, Insurance>? = null
    var mot_details: HashMap<String, Mot>? = null
    var log_book: HashMap<String, Logbook>? = null
    var ph_car: HashMap<String, PrivateHireVehicle>? = null

    constructor()
    constructor(
        driver_license: HashMap<String, DriversLicense>?,
        private_hire: HashMap<String, PrivateHireLicense>?,
        vehicle_details: HashMap<String, VehicleProfile>?,
        insurance_details: HashMap<String, Insurance>?,
        mot_details: HashMap<String, Mot>?,
        log_book: HashMap<String, Logbook>?,
        private_hire_vehicle: HashMap<String, PrivateHireVehicle>?
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