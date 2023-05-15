package h_mal.appttude.com.driver

import android.net.Uri
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId


open class FormRobot : BaseTestRobot() {
    fun submit() = clickButton(R.id.submit)
    fun setDate(datePickerLaunchViewId: Int, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        onView(withId(datePickerLaunchViewId)).perform(click())
        selectDateInPicker(year, monthOfYear, dayOfMonth)
        // click ok in date picker
        onView(withId(android.R.id.button1)).perform(click())
    }

    fun selectSingleImage(imagePickerLauncherViewId: Int, filePath: String) {
        selectSingleImageFromGallery(filePath) {
            onView(withId(imagePickerLauncherViewId)).perform(click())
        }
        // click ok in date picker
    }

    fun selectMultipleImage(imagePickerLauncherViewId: Int, filePaths: Array<String>) {
        selectMultipleImageFromGallery(filePaths) {
            onView(withId(imagePickerLauncherViewId)).perform(click())
        }
    }
}