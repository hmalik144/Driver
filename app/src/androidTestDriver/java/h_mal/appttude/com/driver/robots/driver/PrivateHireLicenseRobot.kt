package h_mal.appttude.com.driver.robots.driver

import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.model.PrivateHireLicense

fun privateHireLicenseRobot(func: PrivateHireLicenseRobot.() -> Unit) =
    PrivateHireLicenseRobot().apply { func() }

class PrivateHireLicenseRobot : FormRobot<PrivateHireLicense>() {

    fun enterLicenseNumber(text: String) = fillEditText(R.id.ph_no, text)
    fun enterLicenseExpiry(date: String) = setDate(R.id.ph_expiry, date)

    fun selectImage(fileName: String) = selectSingleImage(R.id.uploadphlic, fileName)

    override fun submitForm(data: PrivateHireLicense) {
        selectImage(data.phImageString!!)
        enterLicenseNumber(data.phNumber!!)
        enterLicenseExpiry(data.phExpiry!!)
        super.submitForm(data)
    }

    fun validate(data: PrivateHireLicense) {
        checkImageViewDoesNotHaveDefaultImage(R.id.imageView2)
        matchText(R.id.ph_expiry, data.phExpiry!!)
        matchText(R.id.ph_no, data.phNumber!!)
    }

}