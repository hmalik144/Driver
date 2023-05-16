package h_mal.appttude.com.driver.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.CustomViewHolder

fun home(func: HomeRobot.() -> Unit) = HomeRobot().apply { func() }
class HomeRobot : BaseTestRobot() {

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

    fun clickOnItem(text: String) = clickRecyclerItemWithText<CustomViewHolder<*>>(R.id.recycler_view, text)

}