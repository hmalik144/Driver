package h_mal.appttude.com.driver

import android.R
import android.app.Activity
import android.view.View
import androidx.annotation.StringRes
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import h_mal.appttude.com.driver.base.BaseActivity
import h_mal.appttude.com.driver.helpers.BaseViewAction
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf
import org.junit.After
import org.junit.Before


open class BaseUiTest<T : BaseActivity<*, *>>(
    private val activity: Class<T>
) {

    private lateinit var mActivityScenarioRule: ActivityScenario<T>
    private var mIdlingResource: IdlingResource? = null

    private lateinit var currentActivity: Activity

    @Before
    fun setup() {
        beforeLaunch()
        mActivityScenarioRule = ActivityScenario.launch(activity)
        mActivityScenarioRule.onActivity {
            mIdlingResource = it.getIdlingResource()!!
            IdlingRegistry.getInstance().register(mIdlingResource)
            afterLaunch()
        }
    }

    @After
    fun tearDown() {
        mIdlingResource?.let {
            IdlingRegistry.getInstance().unregister(it)
        }
    }

    fun getResourceString(@StringRes stringRes: Int): String {
        return getInstrumentation().targetContext.resources.getString(
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
    open fun afterLaunch() {}

    fun checkToastMessage(message: String) {
        onView(withText(message)).inRoot(
            withDecorView(
                not(getCurrentActivity().window.decorView)
            )
        ).check(matches(isDisplayed()))
    }

    fun checkSnackBarDisplayedByMessage(message: String) {
        onView(
            CoreMatchers.allOf(
                withId(com.google.android.material.R.id.snackbar_text),
                withText(message)
            )
        ).check(matches(isDisplayed()))
    }

    private fun getCurrentActivity(): Activity {
        onView(AllOf.allOf(withId(R.id.content), isDisplayed()))
            .perform(object : BaseViewAction() {
                override fun setPerform(uiController: UiController?, view: View?) {
                    if (view?.context is Activity) {
                        currentActivity = view.context as Activity
                    }
                }
            })
        return currentActivity
    }
}