package h_mal.appttude.com.driver.robots

import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.PASSWORD
import h_mal.appttude.com.driver.R


fun login(func: LoginRobot.() -> Unit) = LoginRobot().apply { func() }
class LoginRobot : BaseTestRobot() {

    fun setEmail(email: String?) = fillEditText(R.id.email, email)

    fun setPassword(pass: String) = fillEditText(R.id.password, pass)

    fun clickLogin() = clickButton(R.id.email_sign_in_button)

    fun clickRegister() = clickButton(R.id.register_button)

    fun clickForgotPassword() = clickButton(R.id.forgot)

    fun checkEmailError(err: String) = checkErrorOnTextEntry(R.id.email, err)

    fun checkPasswordError(err: String) = checkErrorOnTextEntry(R.id.password, err)

    fun attemptLogin(emailAddress: String, password: String = PASSWORD) {
        matchViewWaitFor(R.id.email)
        setEmail(emailAddress)
        setPassword(password)
        clickLogin()
    }

}