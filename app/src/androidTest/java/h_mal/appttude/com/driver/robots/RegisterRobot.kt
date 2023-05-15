package h_mal.appttude.com.driver.robots

import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.R

fun register(func: RegisterRobot.() -> Unit) = RegisterRobot().apply { func() }
class RegisterRobot : BaseTestRobot() {

    fun setName(name: String) = fillEditText(R.id.name_register, name)

    fun setEmail(email: String) = fillEditText(R.id.email, email)

    fun setPassword(pass: String) = fillEditText(R.id.password_top, pass)

    fun setPasswordConfirm(pass: String) = fillEditText(R.id.password_bottom, pass)

    fun clickLogin() = clickButton(R.id.submit)

    fun checkNameError(err: String) = checkErrorOnTextEntry(R.id.name_register, err)

    fun checkEmailError(err: String) = checkErrorOnTextEntry(R.id.email, err)

    fun checkPasswordError(err: String) = checkErrorOnTextEntry(R.id.password_top, err)

    fun checkPasswordConfirmError(err: String) = checkErrorOnTextEntry(R.id.password_bottom, err)

}