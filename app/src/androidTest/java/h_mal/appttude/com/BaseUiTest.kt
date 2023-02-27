package h_mal.appttude.com

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.IdlingRegistry
import androidx.test.rule.ActivityTestRule
import h_mal.appttude.com.espresso.IdlingResourceClass
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Rule

abstract class BaseUiTest<T : AppCompatActivity> {

    @Ignore
    abstract fun getApplicationClass(): Class<T>

    @get:Rule
    var mActivityTestRule = ActivityTestRule(getApplicationClass())

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUp() {
            IdlingRegistry.getInstance().register(IdlingResourceClass.countingIdlingResource)
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            IdlingRegistry.getInstance().unregister(IdlingResourceClass.countingIdlingResource)
        }
    }

    fun getResourceString(@StringRes stringRes:  Int): String {
        return mActivityTestRule.activity.getString(stringRes)
    }
}