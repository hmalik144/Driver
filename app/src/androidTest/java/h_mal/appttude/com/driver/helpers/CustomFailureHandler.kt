package h_mal.appttude.com.driver.helpers

import android.content.Context
import android.view.View
import androidx.test.espresso.FailureHandler
import androidx.test.espresso.base.DefaultFailureHandler
import org.hamcrest.Matcher
import tools.fastlane.screengrab.Screengrab


class CustomFailureHandler(targetContext: Context) : FailureHandler {
    private val delegate: FailureHandler

    init {
        delegate = DefaultFailureHandler(targetContext)
    }

    override fun handle(error: Throwable, viewMatcher: Matcher<View>) {
        delegate.handle(error, viewMatcher)
        // Catch a screenshot on failure
        Screengrab.screenshot("error")
    }
}