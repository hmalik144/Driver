package h_mal.appttude.com.driver.tests.newUser

import h_mal.appttude.com.driver.robots.home

open class DriverProfileTest : DataSubmissionTest() {

    override fun afterLaunch() {
        super.afterLaunch()
        home {
            waitFor(1500)
            openDriverProfile()
        }
    }
}