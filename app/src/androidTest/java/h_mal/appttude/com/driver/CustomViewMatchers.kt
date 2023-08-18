package h_mal.appttude.com.driver

import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.google.android.material.textfield.TextInputLayout
import h_mal.appttude.com.driver.helpers.BaseMatcher
import org.hamcrest.Matcher


/**
 * Matcher for testing error of TextInputLayout
 */
fun checkErrorMessage(expectedErrorText: String): Matcher<View> {
    return object : BaseMatcher<View>() {
        override fun match(item: View): Boolean {
            if (item is EditText) {
                return item.error.toString() == expectedErrorText
            }

            if (item !is TextInputLayout) return false

            val error = item.error ?: return false
            return expectedErrorText == error.toString()
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun checkImage(): Matcher<View> {
    return object: BaseMatcher<ImageView>() {
        override fun match(item: ImageView): Boolean = hasImage(item)

        private fun hasImage(view: ImageView): Boolean {
            val drawable = view.drawable
            var hasImage = drawable != null
            if (hasImage && drawable is BitmapDrawable) {
                hasImage = drawable.bitmap != null
            }
            return hasImage
        }
    } as Matcher<View>
}

