package h_mal.appttude.com.driver.tests.newUser

import h_mal.appttude.com.driver.robots.home

open class VehicleProfileTest : DataSubmissionTest() {

    override fun afterLaunch() {
        super.afterLaunch()
        home {
            openVehicleProfile()
        }
    }
}