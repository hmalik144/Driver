package h_mal.appttude.com.robots

import h_mal.appttude.com.BaseTestRobot
import h_mal.appttude.com.R

fun home(func: HomeRobot.() -> Unit) = HomeRobot().apply { func() }
class HomeRobot: BaseTestRobot() {

    fun checkTitleExists(title: String) = matchText(R.id.prova_title_tv, title)

    fun clickLogin() = clickButton(R.id.email_sign_in_button)

    fun clickRegister() = clickButton(R.id.register_button)

    fun clickForgotPassword() = clickButton(R.id.forgot)

}