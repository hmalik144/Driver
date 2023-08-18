package h_mal.appttude.com.driver.robots.vehicle

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isNotChecked
import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.helpers.EspressoHelper.setChecked
import h_mal.appttude.com.driver.model.VehicleProfile

fun vehicleProfile(func: VehicleProfileRobot.() -> Unit) = VehicleProfileRobot().apply { func() }
class VehicleProfileRobot : FormRobot<VehicleProfile>() {

    fun enterRegistration(reg: String) = scrollAndFillEditText(R.id.reg, reg)
    fun enterMake(make: String) = scrollAndFillEditText(R.id.make, make)
    fun enterModel(model: String) = scrollAndFillEditText(R.id.car_model, model)
    fun enterColour(colour: String) = scrollAndFillEditText(R.id.colour, colour)
    fun enterAddress(address: String) = scrollAndFillEditText(R.id.address, address)
    fun enterPostcode(postCode: String) = scrollAndFillEditText(R.id.postcode, postCode)
    fun enterKeeperName(name: String) = scrollAndFillEditText(R.id.keeper_name, name)
    fun enterDateFirstAvailable(date: String) = scrollAndSetDate(R.id.start_date, date)
    fun isSeized(seized: Boolean) = matchView(R.id.seized_checkbox).perform(setChecked(seized))


    override fun submitForm(data: VehicleProfile) {
        enterRegistration(data.reg!!)
        enterMake(data.make!!)
        enterModel(data.model!!)
        enterColour(data.colour!!)
        enterAddress(data.keeperAddress!!)
        enterPostcode(data.keeperPostCode!!)
        enterKeeperName(data.keeperName!!)
        enterDateFirstAvailable(data.startDate!!)
        isSeized(data.isSeized)
        super.submitForm(data)
    }

    override fun validateSubmission(data: VehicleProfile) {
        matchText(R.id.reg, data.reg!!)
        matchText(R.id.make, data.make!!)
        matchText(R.id.car_model, data.model!!)
        matchText(R.id.colour, data.colour!!)
        matchText(R.id.address, data.keeperAddress!!)
        matchText(R.id.postcode, data.keeperPostCode!!)
        matchText(R.id.keeper_name, data.keeperName!!)
        matchText(R.id.start_date, data.startDate!!)
        val checking = if (data.isSeized) isChecked() else isNotChecked()
        matchView(R.id.seized_checkbox).check(matches(checking))
        super.validateSubmission(data)
    }
}