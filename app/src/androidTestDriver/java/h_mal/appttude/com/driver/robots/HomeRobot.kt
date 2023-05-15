package h_mal.appttude.com.driver.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.R

fun home(func: HomeRobot.() -> Unit) = HomeRobot().apply { func() }
class HomeRobot : BaseTestRobot() {

    fun checkTitleExists(title: String) = matchText(R.id.prova_title_tv, title)

    fun openDrawer() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
    }

    fun closeDrawer() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.close())
    }

    fun updateProfile() {
        openDrawer()
        clickButton(R.id.nav_user_settings)
    }

    fun openDriverProfile() = clickButton(R.id.driver)
    fun openVehicleProfile() = clickButton(R.id.car)

    fun requestProfile() = clickButton(R.id.request_driver_button)
}