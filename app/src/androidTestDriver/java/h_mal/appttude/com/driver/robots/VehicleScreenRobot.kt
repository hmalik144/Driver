package h_mal.appttude.com.driver.robots

import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.R

fun vehicleScreen(func: VehicleScreenRobot.() -> Unit) = VehicleScreenRobot().apply { func() }
class VehicleScreenRobot : BaseTestRobot() {

    fun vehicleProfile() = clickButton(R.id.vehicle_prof)
    fun insurance() = clickButton(R.id.insurance)
    fun mot() = clickButton(R.id.mot)
    fun logbook() = clickButton(R.id.logbook)
    fun privateHireVehicleLicense() = clickButton(R.id.private_hire_vehicle_license)

}