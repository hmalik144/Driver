package h_mal.appttude.com.driver.robots.vehicle

import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.FormRobot.FilePath.Companion.getFilePath
import h_mal.appttude.com.driver.R

fun insurance(func: InsuranceRobot.() -> Unit) = InsuranceRobot().apply { func() }
class InsuranceRobot : FormRobot() {

    fun enterInsurance(text: String) = fillEditText(R.id.insurer, text)
    fun enterInsuranceExpiry(year: Int, monthOfYear: Int, dayOfMonth: Int) =
        setDate(R.id.insurance_exp, year, monthOfYear, dayOfMonth)

    fun selectImages() =
        selectMultipleImage(R.id.uploadInsurance, arrayOf(getFilePath(FilePath.INSURANCE)))

    fun submitForm(insurer: String, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        selectImages()
        enterInsurance(insurer)
        enterInsuranceExpiry(year, monthOfYear, dayOfMonth)
        submit()
    }
}