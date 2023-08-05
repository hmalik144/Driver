package h_mal.appttude.com.driver.robots

import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.R

fun driverScreen(func: DriverScreenRobot.() -> Unit) = DriverScreenRobot().apply { func() }
class DriverScreenRobot : BaseTestRobot() {

    fun driverProfile() = clickButton(R.id.driver_prof)
    fun privateHireLicense() = clickButton(R.id.private_hire)
    fun driverLicense() = clickButton(R.id.drivers_license)

}