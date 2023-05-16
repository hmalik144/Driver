package h_mal.appttude.com.driver

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.widget.DatePicker
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import h_mal.appttude.com.driver.helpers.DataHelper
import h_mal.appttude.com.driver.helpers.EspressoHelper.waitForView
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.Matchers
import java.io.File

open class BaseTestRobot {

    fun fillEditText(resId: Int, text: String?): ViewInteraction =
        onView(withId(resId)).perform(
            ViewActions.replaceText(text),
            ViewActions.closeSoftKeyboard()
        )

    fun clickButton(resId: Int): ViewInteraction =
        onView((withId(resId))).perform(ViewActions.click())

    fun matchView(resId: Int): ViewInteraction = onView(withId(resId))

    fun matchViewWaitFor(resId: Int): ViewInteraction = waitForView(withId(resId))

    fun matchText(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
        .check(matches(ViewMatchers.withText(text)))

    fun matchText(resId: Int, text: String): ViewInteraction = matchText(matchView(resId), text)

    fun clickListItem(listRes: Int, position: Int) {
        onData(anything())
            .inAdapterView(allOf(withId(listRes)))
            .atPosition(position).perform(ViewActions.click())
    }

    fun <VH : ViewHolder> clickRecyclerItemWithText(recyclerId: Int, text: String) {
        onView(withId(recyclerId))
            .perform(
                // scrollTo will fail the test if no item matches.
                RecyclerViewActions.scrollTo<VH>(
                    hasDescendant(withText(text))
                )
            )
    }

    fun checkErrorOnTextEntry(resId: Int, errorMessage: String): ViewInteraction =
        onView(withId(resId)).check(matches(checkErrorMessage(errorMessage)))

    fun checkImageViewHasImage(resId: Int): ViewInteraction =
        onView(withId(resId)).check(matches(checkImage()))

    fun swipeDown(resId: Int): ViewInteraction =
        onView(withId(resId)).perform(swipeDown())

    fun getStringFromResource(@StringRes resId: Int): String =
        Resources.getSystem().getString(resId)

    fun selectDateInPicker(year: Int, month: Int, day: Int) {
        onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker::class.java.name))).perform(
            PickerActions.setDate(
                year,
                month,
                day
            )
        )
    }

    fun selectSingleImageFromGallery(filePath: FormRobot.FilePath, openSelector: () -> Unit) {
        Intents.init()
        // Build the result to return when the activity is launched.
        val resultData = Intent()
        resultData.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        resultData.data = Uri.fromFile(File(FormRobot.FilePath.getFilePath(filePath)))
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)
        // Set up result stubbing when an intent sent to image picker is seen.
        intending(hasAction(Intent.ACTION_GET_CONTENT)).respondWith(result)

        openSelector()
        Intents.release()
    }

    fun selectMultipleImageFromGallery(filePaths: Array<String>, openSelector: () -> Unit) {
        Intents.init()
        // Build the result to return when the activity is launched.
        val resultData = Intent()
        val clipData = DataHelper.createClipData(filePaths)
        resultData.clipData = clipData
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)
        // Set up result stubbing when an intent sent to "contacts" is seen.
        intending(IntentMatchers.toPackage("android.intent.action.PICK")).respondWith(result)

        openSelector()
        Intents.release()
    }
}