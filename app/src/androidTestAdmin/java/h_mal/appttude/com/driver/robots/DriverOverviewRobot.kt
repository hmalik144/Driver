package h_mal.appttude.com.driver.robots

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.R
import org.hamcrest.CoreMatchers.anything


fun driverOverview(func: DriverOverviewRobot.() -> Unit) = DriverOverviewRobot().apply { func() }
class DriverOverviewRobot : BaseTestRobot() {

    fun clickOnItemAtPosition(index: Int) =
        onData(anything())
            .inAdapterView(withId(R.id.approvals_list))
            .atPosition(index)
            .perform(click())

    fun matchView(position: Int, status: String) =
        onData(anything())
            .inAdapterView(withId(R.id.approvals_list))
            .atPosition(position)
            .onChildView(withText(status))
            .check(matches(isDisplayed()))
}