package h_mal.appttude.com.driver

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.provider.MediaStore
import android.widget.DatePicker
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.isInternal
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import h_mal.appttude.com.driver.helpers.DataHelper
import h_mal.appttude.com.driver.helpers.DataHelper.addFilePaths
import org.hamcrest.CoreMatchers.*
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

    fun matchText(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
        .check(matches(ViewMatchers.withText(text)))

    fun matchText(resId: Int, text: String): ViewInteraction = matchText(matchView(resId), text)

    fun clickListItem(listRes: Int, position: Int) {
        onData(anything())
            .inAdapterView(allOf(withId(listRes)))
            .atPosition(position).perform(ViewActions.click())
    }

    fun checkErrorOnTextEntry(resId: Int, errorMessage: String): ViewInteraction =
        onView(withId(resId)).check(matches(checkErrorMessage(errorMessage)))

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

    fun selectSingleImageFromGallery(filePath: String, openSelector: () -> Unit) {
        Intents.init()
        // Build the result to return when the activity is launched.
        val resultData = Intent()
        resultData.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        resultData.data = Uri.fromFile(File(filePath))
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)
        // Set up result stubbing when an intent sent to image picker is seen.
        intending(hasAction(Intent.ACTION_GET_CONTENT)).respondWith(result)

        openSelector()
        Intents.release()
    }

    fun selectMultipleImageFromGallery(filePaths: Array<String>, openSelector: () -> Unit) {
        Intents.init()
        openSelector()
        // Build the result to return when the activity is launched.
        val resultData = Intent()
        val clipData = DataHelper.createClipData(filePaths[0])
        val remainingFiles = filePaths.copyOfRange(1, filePaths.size-1)
        clipData.addFilePaths(remainingFiles)
        resultData.clipData = clipData
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)

        // Set up result stubbing when an intent sent to "contacts" is seen.
        Intents.intending(IntentMatchers.toPackage("android.intent.action.PICK")).respondWith(result)
        Intents.release()
    }
}