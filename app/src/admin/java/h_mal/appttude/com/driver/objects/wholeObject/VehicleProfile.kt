package h_mal.appttude.com.driver.objects.wholeObject

import h_mal.appttude.com.driver.model.Insurance
import h_mal.appttude.com.driver.model.Logbook
import h_mal.appttude.com.driver.model.Mot
import h_mal.appttude.com.driver.model.PrivateHireVehicle
import h_mal.appttude.com.driver.model.VehicleProfile

data class VehicleProfile (
    var insurance_details: Insurance? = Insurance(),
    var log_book: Logbook? = Logbook(),
    var mot_details: Mot? = Mot(),
    var vehicle_details: VehicleProfile? = VehicleProfile(),
    var privateHireVehicle: PrivateHireVehicle? = PrivateHireVehicle()
)