package h_mal.appttude.com.driver.helpers

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.test.espresso.FailureHandler
import androidx.test.espresso.base.DefaultFailureHandler
import com.squareup.spoon.Spoon
import org.hamcrest.Matcher


class SpoonFailureHandler(targetContext: Context) : FailureHandler {
    private val delegate: FailureHandler
    private val context: Context

    init {
        delegate = DefaultFailureHandler(targetContext)
        context = targetContext
    }

    override fun handle(error: Throwable?, viewMatcher: Matcher<View?>?) {
        delegate.handle(error, viewMatcher)
        if (context is Activity) {
            Spoon.screenshot(context, "error")
        }
    }
}