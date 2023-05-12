package h_mal.appttude.com.driver.robots

import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.R

fun driversLicense(func: DriversLicenseRobot.() -> Unit) = DriversLicenseRobot().apply { func() }
class DriversLicenseRobot : BaseTestRobot() {

    fun enterLicenseNumber(text: String) = fillEditText(R.id.lic_no, text)
    fun enterLicenseExpiry(text: String) = fillEditText(R.id.lic_expiry, text)
    fun selectImage() = clickButton(R.id.search_image)
    fun clickSubmit() = clickButton(R.id.submit)

    fun submitForm(licenseNumber: String, licenseExpiry: String) {
        selectImage()
        // TODO: select image in gallery
        enterLicenseNumber(licenseNumber)
        enterLicenseExpiry(licenseExpiry)
        clickSubmit()
    }
}