package h_mal.appttude.com

import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


/**
 * Matcher for testing error of TextInputLayout
 */
fun checkErrorMessage(expectedErrorText: String): Matcher<View?>? {
    return object : TypeSafeMatcher<View?>() {
        override fun matchesSafely(view: View?): Boolean {
            if (view is EditText) {
                return view.error.toString() == expectedErrorText
            }

            if (view !is TextInputLayout) return false

            val error = view.error ?: return false
            return expectedErrorText == error.toString()
        }
        override fun describeTo(d: Description?) {}
    }
}

