package h_mal.appttude.com.robots

import h_mal.appttude.com.BaseTestRobot
import h_mal.appttude.com.R


fun login(func: LoginRobot.() -> Unit) = LoginRobot().apply { func() }
class LoginRobot: BaseTestRobot() {

    fun setEmail(email: String) = fillEditText(R.id.email, email);

    fun setPassword(pass: String) = fillEditText(R.id.password, pass)

    fun clickLogin() = clickButton(R.id.email_sign_in_button)

    fun clickRegister() = clickButton(R.id.register_button)

    fun clickForgotPassword() = clickButton(R.id.forgot)

    fun checkEmailError(err: String) = checkErrorOnTextEntry(R.id.email, err)

    fun checkPasswordError(err: String) = checkErrorOnTextEntry(R.id.password, err)

}