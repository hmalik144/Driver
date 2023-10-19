package h_mal.appttude.com.driver

import android.Manifest
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
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.util.Checks
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.GrantPermissionRule
import com.google.gson.Gson
import h_mal.appttude.com.driver.base.BaseActivity
import h_mal.appttude.com.driver.helpers.BaseViewAction
import h_mal.appttude.com.driver.helpers.SnapshotRule
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import tools.fastlane.screengrab.Screengrab
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy
import tools.fastlane.screengrab.locale.LocaleTestRule
import java.io.BufferedReader


open class BaseUiTest<T : BaseActivity<*, *>>(
    private val activity: Class<T>
) {
    val gson by lazy { Gson() }

    private lateinit var mActivityScenarioRule: ActivityScenario<T>
    private var mIdlingResource: IdlingResource? = null

    private lateinit var currentActivity: Activity

    @get:Rule
    var permissionRule = GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE)

    @get:Rule
    var snapshotRule: SnapshotRule = SnapshotRule()

    @Rule
    @JvmField
    var localeTestRule = LocaleTestRule()

    @Before
    fun setup() {
        Screengrab.setDefaultScreenshotStrategy(UiAutomatorScreenshotStrategy())
        beforeLaunch()
        mActivityScenarioRule = ActivityScenario.launch(activity)
        mActivityScenarioRule.onActivity {
            runBlocking {
                mIdlingResource = it.getIdlingResource()!!
                IdlingRegistry.getInstance().register(mIdlingResource)
            }
        }
        afterLaunch()
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
    open fun afterLaunch() {}

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

    fun <T: Any> readDataFromAsset(fileName: String, clazz: Class<T>): T {
        val iStream =
            getInstrumentation().context.assets.open("$fileName.json")
        val data = iStream.bufferedReader().use(BufferedReader::readText)
        return gson.fromJson(data, clazz)
    }

    inline fun <reified M: Any> readDataFromAsset(fileName: String): M {
        val iStream =
            getInstrumentation().context.assets.open("$fileName.json")
        val data = iStream.bufferedReader().use(BufferedReader::readText)
        return fromJson<M>(data)
    }

    inline fun <reified M> fromJson(json: String)
            = gson.fromJson<M>(json, M::class.java)

}