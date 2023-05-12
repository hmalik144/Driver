package h_mal.appttude.com.driver.robots

import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.R

fun updateProfile(func: UpdateProfileRobot.() -> Unit) = UpdateProfileRobot().apply { func() }
class UpdateProfileRobot : BaseTestRobot() {

    fun enterName(name: String) = fillEditText(R.id.update_name, name)
    fun selectImage() = clickButton(R.id.profile_img)
    fun submitProfileUpdate() = clickButton(R.id.submit_update_profile)

    fun submitForm(name: String) {
        selectImage()
        // TODO: select image in gallery
        enterName(name)
        submitProfileUpdate()
    }
}