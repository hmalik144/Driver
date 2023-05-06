package h_mal.appttude.com.driver.objects.wholeObject

import h_mal.appttude.com.driver.model.*
import h_mal.appttude.com.driver.model.VehicleProfile

data class VehicleProfile (
    var insurance_details: Insurance? = Insurance(),
    var log_book: Logbook? = Logbook(),
    var mot_details: Mot? = Mot(),
    var vehicle_details: VehicleProfile? = VehicleProfile(),
    var privateHireVehicle: PrivateHireVehicle? = PrivateHireVehicle()
)