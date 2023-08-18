package h_mal.appttude.com.driver.robots.driver

import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.scrollTo
import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.model.DriverProfile

fun driversProfile(func: DriversProfileRobot.() -> Unit) = DriversProfileRobot().apply { func() }
class DriversProfileRobot : FormRobot<DriverProfile>() {

    fun enterName(name: String) = fillEditText(R.id.names_input, name)
    fun enterAddress(address: String) = fillEditText(R.id.address_input, address)
    fun enterPostcode(postcode: String) = fillEditText(R.id.postcode_input, postcode)
    fun enterDateOfBirth(date: String) = setDate(R.id.dob_input, date)

    fun enterNINumber(niNumber: String) = fillEditText(R.id.ni_number, niNumber)
    fun enterDateFirstAvailable(date: String) {
        closeSoftKeyboard()
        matchView(R.id.date_first).perform(scrollTo())
        setDate(R.id.date_first, date)
    }

    override fun validateSubmission(data: DriverProfile) {
        checkImageViewDoesNotHaveDefaultImage(R.id.driver_pic)
        matchText(R.id.names_input, data.forenames!!)
    }

    override fun submitForm(data: DriverProfile) = data.run {
        selectSingleImage(R.id.add_photo, driverPic!!)
        enterName(forenames!!)
        enterAddress(address!!)
        enterPostcode(postcode!!)
        enterDateOfBirth(dob!!)
        enterNINumber(ni!!)
        enterDateFirstAvailable(dateFirst!!)
        super.submitForm(data)
    }
}