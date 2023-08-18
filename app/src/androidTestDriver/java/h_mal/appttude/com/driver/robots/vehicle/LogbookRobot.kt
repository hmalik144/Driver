package h_mal.appttude.com.driver.robots.vehicle

import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.model.Logbook

fun logbook(func: LogbookRobot.() -> Unit) = LogbookRobot().apply { func() }
class LogbookRobot : FormRobot<Logbook>() {

    fun enterV5c(v5c: String) = fillEditText(R.id.v5c_no, v5c)

    override fun submitForm(data: Logbook) {
        selectSingleImage(R.id.upload_lb, data.photoString!!)
        enterV5c(data.v5cnumber!!)
        super.submitForm(data)
    }

    override fun validateSubmission(data: Logbook) {
        checkImageViewDoesNotHaveDefaultImage(R.id.log_book_img)
        matchText(R.id.v5c_no, data.v5cnumber!!)
    }
}