package h_mal.appttude.com.driver.robots.driver

import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R

fun driversLicense(func: DriversLicenseRobot.() -> Unit) = DriversLicenseRobot().apply { func() }
class DriversLicenseRobot : FormRobot() {

    fun enterLicenseNumber(text: String) = fillEditText(R.id.lic_no, text)
    fun enterLicenseExpiry(year: Int, monthOfYear: Int, dayOfMonth: Int) =
        setDate(R.id.lic_expiry, year, monthOfYear, dayOfMonth)

    fun selectImage() = selectSingleImage(R.id.search_image, FilePath.LICENSE)

    fun submitForm(licenseNumber: String, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        selectImage()
        enterLicenseNumber(licenseNumber)
        enterLicenseExpiry(year, monthOfYear, dayOfMonth)
        submit()
    }

    fun validate() {
        checkImageViewHasImage(R.id.driversli_img)

    }

}