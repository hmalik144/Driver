package h_mal.appttude.com.driver.robots.driver

import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.model.Date

fun driversProfile(func: DriversProfileRobot.() -> Unit) = DriversProfileRobot().apply { func() }
class DriversProfileRobot : FormRobot() {

    fun enterName(name: String) = fillEditText(R.id.names_input, name)
    fun enterAddress(address: String) = fillEditText(R.id.address_input, address)
    fun enterPostcode(postcode: String) = fillEditText(R.id.postcode_input, postcode)
    fun enterDateOfBirth(dob: String) = fillEditText(R.id.dob_input, dob)
    fun enterDateOfBirth(dob: Date) = setDate(R.id.dob_input, dob.year, dob.monthOfYear, dob.dayOfMonth)
    fun enterNINumber(niNumber: String) = fillEditText(R.id.ni_number, niNumber)
    fun enterDateFirstAvailable(date: Date) =
        setDate(R.id.date_first, date.year, date.monthOfYear, date.dayOfMonth)

    fun selectImage() = selectSingleImage(R.id.add_photo, FilePath.PROFILE_PIC)

    fun submitForm(
        name: String,
        address: String,
        postcode: String,
        dob: Date,
        niNumber: String,
        firstDateAvailable: Date
    ) {
        selectImage()
        enterName(name)
        enterAddress(address)
        enterPostcode(postcode)
        enterDateOfBirth(dob)
        enterNINumber(niNumber)
        enterDateFirstAvailable(firstDateAvailable)
        submit()
    }
}