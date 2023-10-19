package h_mal.appttude.com.driver

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import h_mal.appttude.com.driver.helpers.EspressoHelper.trying
import h_mal.appttude.com.driver.base.Model
import org.hamcrest.CoreMatchers.allOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter


open class FormRobot<T : Model> : BaseTestRobot() {

    fun submit() = onView(
        allOf(
            withId(R.id.submit),
            isAssignableFrom(com.google.android.material.button.MaterialButton::class.java)
        )
    ).perform(click())

    fun setDate(datePickerLaunchViewId: Int, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        onView(withId(datePickerLaunchViewId)).perform(click())
        selectDateInPicker(year, monthOfYear, dayOfMonth)
        // click ok in date picker
        onView(withId(android.R.id.button1)).perform(click())
    }

    fun setDate(datePickerLaunchViewId: Int, dateString: String) {
        onView(withId(datePickerLaunchViewId)).perform(click())
        val date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        selectDateInPicker(date.year, date.monthValue, date.dayOfMonth)
        // click ok in date picker
        onView(withId(android.R.id.button1)).perform(click())
    }

    fun scrollAndSetDate(datePickerLaunchViewId: Int, dateString: String) {
        onView(withId(datePickerLaunchViewId)).perform(scrollTo(), click())
        val date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        selectDateInPicker(date.year, date.monthValue, date.dayOfMonth)
        // click ok in date picker
        onView(withId(android.R.id.button1)).perform(click())
    }

    fun selectSingleImage(imagePickerLauncherViewId: Int, fileName: String) {
        selectSingleImageFromGallery(fileName) {
            onView(withId(imagePickerLauncherViewId)).perform(click())
        }
    }

    fun selectSingleImage(imagePickerViewInteraction: ViewInteraction, fileName: String) {
        selectSingleImageFromGallery(fileName) {
            imagePickerViewInteraction.perform(click())
        }
    }

    fun selectMultipleImage(imagePickerLauncherViewId: Int, filePaths: List<String>) {
        selectMultipleImageFromGallery(filePaths.map { "/sdcard/Camera/$it" }) {
            onView(withId(imagePickerLauncherViewId)).perform(click())
        }
    }

    open fun submitForm(data: T) {
        (trying {
            onView(withId(R.id.submit)).perform(scrollTo())
        } ?: onView(withId(R.id.submit))).perform(click())
    }

    open fun validateSubmission(data: T) {}

    open fun submitAndValidate(data: T) {
        submitForm(data)
        validateSubmission(data)
    }
}