package h_mal.appttude.com.driver.robots.vehicle

import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.model.PrivateHireVehicle

fun privateHireVehicleLicense(func: PrivateHireVehicleLicenseRobot.() -> Unit) =
    PrivateHireVehicleLicenseRobot().apply { func() }
class PrivateHireVehicleLicenseRobot : FormRobot<PrivateHireVehicle>() {

    fun enterLicenseNumber(text: String) = fillEditText(R.id.ph_no, text)
    fun enterLicenseExpiry(date: String) = setDate(R.id.ph_expiry, date)

    override fun submitForm(data: PrivateHireVehicle) {
        selectSingleImage(
            matchText(R.string.upload_private_hire_photo),
            data.phCarImageString!!
        )
        enterLicenseNumber(data.phCarNumber!!)
        enterLicenseExpiry(data.phCarExpiry!!)
        super.submitForm(data)
    }

    override fun validateSubmission(data: PrivateHireVehicle) {
        checkImageViewDoesNotHaveDefaultImage(R.id.imageView2)
        matchText(R.id.ph_no, data.phCarNumber!!)
        matchText(R.id.ph_expiry, data.phCarExpiry!!)
    }
}