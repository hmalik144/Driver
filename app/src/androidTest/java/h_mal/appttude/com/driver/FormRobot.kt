package h_mal.appttude.com.driver

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import h_mal.appttude.com.driver.helpers.getImagePath


open class FormRobot : BaseTestRobot() {
    fun submit() = clickButton(R.id.submit)
    fun setDate(datePickerLaunchViewId: Int, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        onView(withId(datePickerLaunchViewId)).perform(click())
        selectDateInPicker(year, monthOfYear, dayOfMonth)
        // click ok in date picker
        onView(withId(android.R.id.button1)).perform(click())
    }

    fun selectSingleImage(imagePickerLauncherViewId: Int, filePath: FilePath) {
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

    enum class FilePath(val path: String) {
        PROFILE_PIC("driver_profile_pic.jpg"),
        INSURANCE("driver_insurance.jpg"),
        PRIVATE_HIRE("driver_license_private_hire.jpg"),
        PRIVATE_HIRE_CAR("driver_license_private_hire_car.jpg"),
        LOGBOOK("driver_logbook.jpg"),
        MOT("driver_mot.jpg"),
        LICENSE("driver_license_driver.jpg");

        companion object {
            fun getFilePath(filePath: FilePath): String {
                return getImagePath(filePath.path)
            }
        }
    }
}