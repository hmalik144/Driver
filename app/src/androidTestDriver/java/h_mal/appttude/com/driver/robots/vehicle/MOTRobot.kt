package h_mal.appttude.com.driver.robots.vehicle

import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.model.Mot

fun mot(func: MOTRobot.() -> Unit) = MOTRobot().apply { func() }
class MOTRobot : FormRobot<Mot>() {

    fun enterMotExpiry(expiry: String) = setDate(R.id.mot_expiry, expiry)

    override fun submitForm(data: Mot) {
        selectSingleImage(R.id.uploadmot, data.motImageString!!)
        enterMotExpiry(data.motExpiry!!)
        super.submitForm(data)
    }

    override fun validateSubmission(data: Mot) {
        checkImageViewDoesNotHaveDefaultImage(R.id.mot_img)
        matchText(R.id.mot_expiry, data.motExpiry!!)
    }
}