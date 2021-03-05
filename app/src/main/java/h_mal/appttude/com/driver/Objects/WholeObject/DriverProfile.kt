package h_mal.appttude.com.driver.Objects.WholeObject

import h_mal.appttude.com.driver.model.DriverProfileObject
import h_mal.appttude.com.driver.model.DriversLicenseObject
import h_mal.appttude.com.driver.model.PrivateHireObject


class DriverProfile {
    var driver_profile: DriverProfileObject? = null
    var driver_license: DriversLicenseObject? = null
    var private_hire: PrivateHireObject? = null

    constructor(
        driver_profile: DriverProfileObject?,
        driver_license: DriversLicenseObject?,
        private_hire: PrivateHireObject?
    ) {
        this.driver_profile = driver_profile
        this.driver_license = driver_license
        this.private_hire = private_hire
    }

    constructor()
}