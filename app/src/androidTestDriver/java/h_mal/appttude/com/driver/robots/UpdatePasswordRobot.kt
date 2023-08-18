package h_mal.appttude.com.driver.robots

import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.R

fun updatePassword(func: UpdatePasswordRobot.() -> Unit) = UpdatePasswordRobot().apply { func() }
class UpdatePasswordRobot : BaseTestRobot() {

    fun submit() = clickButton(R.id.submit)
    fun enterEmail(email: String) = fillEditText(R.id.email_update, email)
    fun enterPassword(password: String) = fillEditText(R.id.password_top, password)
    fun enterNewPassword(email: String) = fillEditText(R.id.password_bottom, email)
    fun submitDelete() = clickButton(R.id.submit)

    fun submitForm(email: String, password: String, newPassword: String) {
        enterEmail(email)
        enterPassword(password)
        enterNewPassword(newPassword)
        submit()
    }
}