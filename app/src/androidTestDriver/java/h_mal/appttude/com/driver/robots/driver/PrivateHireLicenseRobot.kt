package h_mal.appttude.com.driver.robots.driver

import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R

fun privateHireLicenseRobot(func: PrivateHireLicenseRobot.() -> Unit) =
    PrivateHireLicenseRobot().apply { func() }

class PrivateHireLicenseRobot : FormRobot() {

    fun enterLicenseNumber(text: String) = fillEditText(R.id.ph_no, text)
    fun enterLicenseExpiry(year: Int, monthOfYear: Int, dayOfMonth: Int) =
        setDate(R.id.ph_expiry, year, monthOfYear, dayOfMonth)

    fun selectImage() = selectSingleImage(R.id.uploadphlic, FilePath.PRIVATE_HIRE)

    fun submitForm(licenseNumber: String, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        selectImage()
        enterLicenseNumber(licenseNumber)
        enterLicenseExpiry(year, monthOfYear, dayOfMonth)
        submit()
    }
}