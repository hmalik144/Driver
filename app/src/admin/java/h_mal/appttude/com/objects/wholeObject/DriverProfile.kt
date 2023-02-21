package h_mal.appttude.com.objects.wholeObject

import h_mal.appttude.com.model.DriverProfileObject
import h_mal.appttude.com.model.DriversLicenseObject
import h_mal.appttude.com.model.PrivateHireObject


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