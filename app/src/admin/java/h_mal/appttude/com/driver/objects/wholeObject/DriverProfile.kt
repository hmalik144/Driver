package h_mal.appttude.com.driver.admin.objects.wholeObject

import h_mal.appttude.com.driver.model.DriverProfile
import h_mal.appttude.com.driver.model.DriversLicense
import h_mal.appttude.com.driver.model.PrivateHireLicense


class DriverProfile {
    var driver_profile: DriverProfile? = null
    var driver_license: DriversLicense? = null
    var private_hire: PrivateHireLicense? = null

    constructor(
        driver_profile: DriverProfile?,
        driver_license: DriversLicense?,
        private_hire: PrivateHireLicense?
    ) {
        this.driver_profile = driver_profile
        this.driver_license = driver_license
        this.private_hire = private_hire
    }

    constructor()
}