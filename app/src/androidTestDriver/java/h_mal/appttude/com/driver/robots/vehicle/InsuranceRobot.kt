package h_mal.appttude.com.driver.robots.vehicle

import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.model.Insurance

fun insurance(func: InsuranceRobot.() -> Unit) = InsuranceRobot().apply { func() }
class InsuranceRobot : FormRobot<Insurance>() {

    fun enterInsurance(text: String) = fillEditText(R.id.insurer, text)
    fun enterInsuranceExpiry(date: String) = setDate(R.id.insurance_exp, date)

    override fun submitForm(data: Insurance) {
        selectMultipleImage(R.id.uploadInsurance, data.photoStrings!!.map { it!! })
        enterInsurance(data.insurerName!!)
        enterInsuranceExpiry(data.expiryDate!!)
        super.submitForm(data)
    }

    override fun validateSubmission(data: Insurance) {
        matchText(R.id.insurer, data.insurerName!!)
        matchText(R.id.insurance_exp, data.expiryDate!!)
    }
}