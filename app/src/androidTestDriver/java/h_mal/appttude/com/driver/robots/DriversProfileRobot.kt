package h_mal.appttude.com.driver.robots

import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.R

fun driversProfile(func: DriversProfileRobot.() -> Unit) = DriversProfileRobot().apply { func() }
class DriversProfileRobot : BaseTestRobot() {

    fun enterLicenseNumber(text: String) = fillEditText(R.id.lic_no, text)
    fun enterLicenseExpiry(text: String) = fillEditText(R.id.lic_expiry, text)
    fun selectImage() = clickButton(R.id.add_photo)
    fun clickSubmit() = clickButton(R.id.submit_driver)

    fun submitForm(licenseNumber: String, licenseExpiry: String) {
        selectImage()
        // TODO: select image in gallery
        enterLicenseNumber(licenseNumber)
        enterLicenseExpiry(licenseExpiry)
        clickSubmit()
    }
}