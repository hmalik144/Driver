package h_mal.appttude.com.driver.robots

import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.R

fun updateEmail(func: UpdateEmailRobot.() -> Unit) = UpdateEmailRobot().apply { func() }
class UpdateEmailRobot : BaseTestRobot() {

    fun submit() = clickButton(R.id.submit)
    fun enterEmail(email: String) = fillEditText(R.id.email_update, email)
    fun enterPassword(password: String) = fillEditText(R.id.password_top, password)
    fun enterNewEmail(email: String) = fillEditText(R.id.new_email, email)

    fun submitForm(email: String, password: String, newEmail: String) {
        enterEmail(email)
        enterPassword(password)
        enterNewEmail(newEmail)
        submit()
    }
}