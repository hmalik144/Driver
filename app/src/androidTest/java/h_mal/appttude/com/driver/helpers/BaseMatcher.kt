package h_mal.appttude.com.driver.helpers

import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

open class BaseMatcher<T: Any>: TypeSafeMatcher<T>() {
    override fun describeTo(description: Description?) { }

    override fun describeMismatchSafely(item: T, mismatchDescription: Description?) {
        describe(item, mismatchDescription)
    }


    override fun matchesSafely(item: T): Boolean = match(item)

    open fun match(item: T): Boolean { return false }

    open fun describe(item: T, mismatchDescription: Description?) {
        super.describeMismatchSafely(item, mismatchDescription)
    }

}