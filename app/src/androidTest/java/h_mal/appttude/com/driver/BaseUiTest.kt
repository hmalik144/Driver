package h_mal.appttude.com.driver

import android.Manifest
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.R
import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.StringRes
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.setFailureHandler
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.Root
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.GrantPermissionRule
import h_mal.appttude.com.driver.base.BaseActivity
import h_mal.appttude.com.driver.helpers.BaseViewAction
import h_mal.appttude.com.driver.helpers.SpoonFailureHandler
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.AllOf
import org.junit.After
import org.junit.Before
import org.junit.Rule


open class BaseUiTest<T : BaseActivity<*, *>>(
    private val activity: Class<T>
) {

    private lateinit var mActivityScenarioRule: ActivityScenario<T>
    private var mIdlingResource: IdlingResource? = null

    private lateinit var currentActivity: Activity

    @JvmField
    @Rule
    var permissionWrite = GrantPermissionRule.grant(WRITE_EXTERNAL_STORAGE)

    @Before
    fun setup() {
        setFailureHandler(SpoonFailureHandler(getInstrumentation().targetContext))
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
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        })
    }

    open fun beforeLaunch() {}
    open fun afterLaunch(context: Context) {}


    @Suppress("DEPRECATION")
    fun checkToastMessage(message: String) {
        onView(withText(message)).inRoot(object : TypeSafeMatcher<Root>() {
            override fun describeTo(description: Description?) {
                description?.appendText("is toast")
            }

            override fun matchesSafely(root: Root): Boolean {
                root.run {
                    if (windowLayoutParams.get().type == WindowManager.LayoutParams.TYPE_TOAST) {
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
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            waitFor(3500)
        }
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