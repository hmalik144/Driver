package h_mal.appttude.com.driver.robots.driver

import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.model.DriversLicense

fun driversLicense(func: DriversLicenseRobot.() -> Unit) = DriversLicenseRobot().apply { func() }
class DriversLicenseRobot : FormRobot<DriversLicense>() {

    fun enterLicenseNumber(text: String) = fillEditText(R.id.lic_no, text)
    fun enterLicenseExpiry(data: String) = setDate(R.id.lic_expiry, data)

    override fun submitForm(data: DriversLicense) {
        selectSingleImage(R.id.search_image, data.licenseImageString!!)
        enterLicenseExpiry(data.licenseExpiry!!)
        enterLicenseNumber(data.licenseNumber!!)
        super.submitForm(data)
    }

    override fun validateSubmission(data: DriversLicense) {
        checkImageViewDoesNotHaveDefaultImage(R.id.driversli_img)
    }

}