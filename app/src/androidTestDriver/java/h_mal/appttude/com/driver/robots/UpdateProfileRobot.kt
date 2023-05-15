package h_mal.appttude.com.driver.robots

import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.helpers.PROFILE_PIC

fun updateProfile(func: UpdateProfileRobot.() -> Unit) = UpdateProfileRobot().apply { func() }
class UpdateProfileRobot : FormRobot() {

    fun enterName(name: String) = fillEditText(R.id.update_name, name)
//    fun selectImage() = selectSingleImage(R.id.profile_img, PROFILE_PIC)

    fun submitForm(name: String) {
//        selectImage()
        enterName(name)
        submit()
    }
}