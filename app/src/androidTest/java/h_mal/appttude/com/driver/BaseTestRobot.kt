package h_mal.appttude.com.driver

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.view.View
import android.widget.DatePicker
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import h_mal.appttude.com.driver.helpers.DataHelper
import h_mal.appttude.com.driver.helpers.EspressoHelper.waitForView
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import java.io.File

@SuppressWarnings("unused")
open class BaseTestRobot {

    fun fillEditText(resId: Int, text: String?): ViewInteraction =
        onView(withId(resId)).perform(
            ViewActions.replaceText(text),
            ViewActions.closeSoftKeyboard()
        )

    fun clickButton(resId: Int): ViewInteraction =
        onView((withId(resId))).perform(click())

    fun matchView(resId: Int): ViewInteraction = onView(withId(resId))

    fun matchViewWaitFor(resId: Int): ViewInteraction = waitForView(withId(resId))

    fun matchText(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
        .check(matches(withText(text)))

    fun matchText(viewId: Int, textId: Int): ViewInteraction = onView(withId(viewId))
        .check(matches(withText(textId)))

    fun matchText(resId: Int, text: String): ViewInteraction = matchText(matchView(resId), text)

    fun clickListItem(listRes: Int, position: Int) {
        onData(anything())
            .inAdapterView(allOf(withId(listRes)))
            .atPosition(position).perform(click())
    }

    fun <VH : ViewHolder> scrollToRecyclerItem(recyclerId: Int, text: String): ViewInteraction? {
        return matchView(recyclerId)
            .perform(
                // scrollTo will fail the test if no item matches.
                RecyclerViewActions.scrollTo<VH>(
                    hasDescendant(withText(text))
                )
            )
    }

    fun <VH : ViewHolder> scrollToRecyclerItem(recyclerId: Int, resIdForString: Int): ViewInteraction? {
        return matchView(recyclerId)
            .perform(
                // scrollTo will fail the test if no item matches.
                RecyclerViewActions.scrollTo<VH>(
                    hasDescendant(withText(resIdForString))
                )
            )
    }

    fun <VH : ViewHolder> scrollToRecyclerItemByPosition(recyclerId: Int, position: Int): ViewInteraction? {
        return matchView(recyclerId)
            .perform(
                // scrollTo will fail the test if no item matches.
                RecyclerViewActions.scrollToPosition<VH>(position)
            )
    }

    fun <VH : ViewHolder> clickViewInRecycler(recyclerId: Int, text: String) {
        matchView(recyclerId)
            .perform(
                // scrollTo will fail the test if no item matches.
                RecyclerViewActions.actionOnItem<VH>(hasDescendant(withText(text)), click())
            )
    }

    fun <VH : ViewHolder> clickViewInRecycler(recyclerId: Int, resIdForString: Int) {
        matchView(recyclerId)
            .perform(
                // scrollTo will fail the test if no item matches.
                RecyclerViewActions.actionOnItem<VH>(hasDescendant(withText(resIdForString)), click())
            )
    }

    fun <VH : ViewHolder> clickSubViewInRecycler(recyclerId: Int, text: String, subView: Int) {
        scrollToRecyclerItem<VH>(recyclerId, text)
            ?.perform(
                // scrollTo will fail the test if no item matches.
                RecyclerViewActions.actionOnItem<VH>(
                    hasDescendant(withText(text)), object : ViewAction {
                        override fun getDescription(): String = "Matching recycler descendant"
                        override fun getConstraints(): Matcher<View>? = isRoot()
                        override fun perform(uiController: UiController?, view: View?) {
                            view?.findViewById<View>(subView)?.performClick()
                        }
                    }
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
        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name))).perform(
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