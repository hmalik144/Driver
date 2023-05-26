package h_mal.appttude.com.driver.objects

import h_mal.appttude.com.driver.model.*


data class ArchiveObject(
    var driver_license: HashMap<String, DriversLicense>? = HashMap(),
    var private_hire: HashMap<String, PrivateHireLicense>? = HashMap(),
    var vehicle_details: HashMap<String, VehicleProfile>? = HashMap(),
    var insurance_details: HashMap<String, Insurance>? = HashMap(),
    var mot_details: HashMap<String, Mot>? = HashMap(),
    var log_book: HashMap<String, Logbook>? = HashMap(),
    var ph_car: HashMap<String, PrivateHireVehicle>? = HashMap(),
)