package h_mal.appttude.com.espresso

import androidx.test.espresso.idling.CountingIdlingResource

object IdlingResourceClass {
    private val CLASS_NAME = "IdlingResourceClass"

    private const val RESOURCE = "GLOBAL"

    @JvmField val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.increment()
        }
    }

    fun decrement() {
        if (countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }

}