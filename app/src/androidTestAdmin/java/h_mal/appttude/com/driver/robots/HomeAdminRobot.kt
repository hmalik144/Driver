package h_mal.appttude.com.driver.robots

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withTagKey
import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.CustomViewHolder
import h_mal.appttude.com.driver.model.DatabaseStatus

fun homeAdmin(func: HomeAdminRobot.() -> Unit) = HomeAdminRobot().apply { func() }
class HomeAdminRobot : BaseTestRobot() {

    fun waitUntilDisplayed() {
        matchViewWaitFor(R.id.recycler_view)
    }

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

    fun clickOnItem(anyText: String) =
        clickViewInRecycler<CustomViewHolder<*>>(R.id.recycler_view, anyText)

    fun clickOnDriverIdentifier(anyText: String) =
        clickSubViewInRecycler<CustomViewHolder<*>>(R.id.recycler_view, anyText, R.id.driver_no)

    fun submitDialog(text: String) {
        onView(withTagKey(R.string.driver_identifier)).perform(
            ViewActions.replaceText(text),
            ViewActions.closeSoftKeyboard()
        )
        // Click OK
        onView(withId(android.R.id.button1)).perform(ViewActions.click())
    }

    fun showNoPermissionsDisplay() {
        matchViewWaitFor(R.id.header)
        matchText(R.id.header, DatabaseStatus.NO_PERMISSION.header)
        matchText(R.id.subtext, DatabaseStatus.NO_PERMISSION.subtext)
    }
}