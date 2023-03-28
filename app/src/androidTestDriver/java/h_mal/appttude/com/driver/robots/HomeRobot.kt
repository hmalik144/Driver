package h_mal.appttude.com.driver.robots

import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.R

fun home(func: HomeRobot.() -> Unit) = HomeRobot().apply { func() }
class HomeRobot : BaseTestRobot() {

    fun checkTitleExists(title: String) = matchText(R.id.prova_title_tv, title)

}