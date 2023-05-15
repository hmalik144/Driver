package h_mal.appttude.com.driver.robots

import h_mal.appttude.com.driver.FormRobot
import h_mal.appttude.com.driver.R

fun delete(func: DeleteRobot.() -> Unit) = DeleteRobot().apply { func() }
class DeleteRobot : FormRobot() {

    fun enterEmail(email: String) = fillEditText(R.id.email_update, email)
    fun enterPassword(password: String) = fillEditText(R.id.password_top, password)

    fun submitForm(email: String, password: String) {
        enterEmail(email)
        enterPassword(password)
        submit()
    }
}