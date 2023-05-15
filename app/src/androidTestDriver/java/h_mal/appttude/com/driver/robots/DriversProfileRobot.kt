package h_mal.appttude.com.driver.robots

import android.text.InputType
import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R

fun driversProfile(func: DriversProfileRobot.() -> Unit) = DriversProfileRobot().apply { func() }
class DriversProfileRobot : FormRobot() {

    fun enterName(name: String) = fillEditText(R.id.names_input, name)
    fun enterAddress(address: String) = fillEditText(R.id.address_input, address)
    fun enterPostcode(postcode: String) = fillEditText(R.id.postcode_input, postcode)
    fun enterDateOfBirth(dob: String) = fillEditText(R.id.dob_input, dob)
    fun enterNINumber(niNumber: String) = fillEditText(R.id.ni_number, niNumber)
    fun enterDateFirstAvailable(year: Int, monthOfYear: Int, dayOfMonth: Int) = setDate(R.id.date_first, year, monthOfYear, dayOfMonth)

    fun selectImage() = clickButton(R.id.add_photo)

    fun submitForm(name: String, address: String, postcode: String, dob: String, niNumber: String, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        selectImage()
        // TODO: select image in gallery
        enterName(name)
        enterAddress(address)
        submit()
    }
}