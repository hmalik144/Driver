package h_mal.appttude.com.driver

import android.view.View
import androidx.annotation.StringRes
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.platform.app.InstrumentationRegistry
import h_mal.appttude.com.driver.base.BaseActivity
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before


open class BaseUiTest<T : BaseActivity<*,*>>(
    private val activity: Class<T>
) {

    private lateinit var mActivityScenarioRule: ActivityScenario<T>
    private var mIdlingResource: IdlingResource? = null

    @Before
    fun setup() {
        beforeLaunch()
        mActivityScenarioRule = ActivityScenario.launch(activity)
        mActivityScenarioRule.onActivity {
            mIdlingResource = it.getIdlingResource()!!
            IdlingRegistry.getInstance().register(mIdlingResource)
        }
    }

    @After
    fun tearDown() {
        mIdlingResource?.let {
            IdlingRegistry.getInstance().unregister(it)
        }
    }

    fun getResourceString(@StringRes stringRes: Int): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.resources.getString(
            stringRes
        )
    }

    fun waitFor(delay: Long) {
        onView(isRoot()).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String? = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        })
    }

    open fun beforeLaunch() {}
}