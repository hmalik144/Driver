package h_mal.appttude.com.driver.robots.vehicle

import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R

fun mot(func: MOTRobot.() -> Unit) = MOTRobot().apply { func() }
class MOTRobot : FormRobot() {

    fun enterV5cNumber(v5c: String) = fillEditText(R.id.mot_expiry, v5c)
    fun selectImages() = selectSingleImage(R.id.mot_expiry, FilePath.LOGBOOK)

    fun submitForm(v5c: String) {
        selectImages()
        enterV5cNumber(v5c)
        submit()
    }
}