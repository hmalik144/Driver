package h_mal.appttude.com.driver

import android.R
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import androidx.annotation.StringRes
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import h_mal.appttude.com.driver.base.BaseActivity
import h_mal.appttude.com.driver.helpers.BaseViewAction
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
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
            afterLaunch(it)
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
    open fun afterLaunch(context: Context) {}

    fun checkToastMessage(message: String) {
        onView(withText(message)).inRoot(object : TypeSafeMatcher<Root>() {
            override fun describeTo(description: Description?) {
                description?.appendText("is toast")
            }

            override fun matchesSafely(root: Root): Boolean {
                root.run {
                    if (windowLayoutParams.get().type === WindowManager.LayoutParams.TYPE_TOAST) {
                        decorView.run {
                            if (windowToken === applicationWindowToken) {
                                // windowToken == appToken means this window isn't contained by any other windows.
                                // if it was a window for an activity, it would have TYPE_BASE_APPLICATION.
                                return true
                            }
                        }
                    }
                }
                return false
            }
        }
        ).check(matches(isDisplayed()))
    }

    fun checkSnackBarDisplayedByMessage(message: String) {
        onView(
            CoreMatchers.allOf(
                withId(com.google.android.material.R.id.snackbar_text),
                withText(message)
            )
        ).check(matches(isDisplayed())).perform(click())
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