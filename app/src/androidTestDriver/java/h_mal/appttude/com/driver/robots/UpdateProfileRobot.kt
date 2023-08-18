package h_mal.appttude.com.driver.robots

import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.R

fun updateProfile(func: UpdateProfileRobot.() -> Unit) = UpdateProfileRobot().apply { func() }
class UpdateProfileRobot : BaseTestRobot() {

    fun submit() = clickButton(R.id.submit)
    fun enterName(name: String) = fillEditText(R.id.update_name, name)

    fun submitForm(name: String) {
        selectSingleImageFromGallery("driver_profile_pic") {
            clickButton(R.id.profile_img)
        }
        enterName(name)
        submit()
    }
}