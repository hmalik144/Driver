package h_mal.appttude.com.driver.robots.vehicle

import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.helpers.EspressoHelper.setChecked

fun vehicleProfile(func: VehicleProfileRobot.() -> Unit) = VehicleProfileRobot().apply { func() }
class VehicleProfileRobot : FormRobot() {

    fun enterRegistration(reg: String) = fillEditText(R.id.reg, reg)
    fun enterMake(make: String) = fillEditText(R.id.make, make)
    fun enterModel(model: String) = fillEditText(R.id.car_model, model)
    fun enterColour(colour: String) = fillEditText(R.id.colour, colour)
    fun enterAddress(address: String) = fillEditText(R.id.address, address)
    fun enterPostcode(postCode: String) = fillEditText(R.id.postcode, postCode)
    fun enterKeeperName(name: String) = fillEditText(R.id.keeper_name, name)
    fun enterDateFirstAvailable(year: Int, monthOfYear: Int, dayOfMonth: Int) =
        setDate(R.id.start_date, year, monthOfYear, dayOfMonth)

    fun isSeized(seized: Boolean) = matchView(R.id.seized_checkbox).perform(setChecked(seized))

    fun submitForm(
        reg: String,
        make: String,
        model: String,
        colour: String,
        address: String,
        postCode: String,
        name: String,
        year: Int,
        monthOfYear: Int,
        dayOfMonth: Int,
        seized: Boolean = false
    ) {
        enterRegistration(reg)
        enterMake(make)
        enterModel(model)
        enterColour(colour)
        enterAddress(address)
        enterPostcode(postCode)
        enterKeeperName(name)
        enterDateFirstAvailable(year, monthOfYear, dayOfMonth)
        isSeized(seized)
        submit()
    }
}