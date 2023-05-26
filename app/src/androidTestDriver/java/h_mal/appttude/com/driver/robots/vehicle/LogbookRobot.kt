package h_mal.appttude.com.driver.robots.vehicle

import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R

fun logbook(func: LogbookRobot.() -> Unit) = LogbookRobot().apply { func() }
class LogbookRobot : FormRobot() {

    fun selectImages() = selectSingleImage(R.id.uploadmot, FilePath.MOT)
    fun enterExpiryDate(year: Int, monthOfYear: Int, dayOfMonth: Int) =
        setDate(R.id.mot_expiry, year, monthOfYear, dayOfMonth)

    fun submitForm(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        selectImages()
        enterExpiryDate(year, monthOfYear, dayOfMonth)
        submit()
    }
}