package h_mal.appttude.com.driver.robots

import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.R

fun update(func: UpdateRobot.() -> Unit) = UpdateRobot().apply { func() }
class UpdateRobot : BaseTestRobot() {

    fun updateEmail() = clickButton(R.id.update_email_button)
    fun updatePassword() = clickButton(R.id.update_password_button)
    fun updateProfile() = clickButton(R.id.update_profile_button)
    fun deleteProfile() = clickButton(R.id.delete_profile)

}