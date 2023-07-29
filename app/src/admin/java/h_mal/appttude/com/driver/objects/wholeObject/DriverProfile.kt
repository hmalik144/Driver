package h_mal.appttude.com.driver.objects.wholeObject

import h_mal.appttude.com.driver.model.DriverProfile
import h_mal.appttude.com.driver.model.DriversLicense
import h_mal.appttude.com.driver.model.PrivateHireLicense


data class DriverProfile(
    var driver_profile: DriverProfile? = DriverProfile(),
    var driver_license: DriversLicense? = DriversLicense(),
    var private_hire: PrivateHireLicense? = PrivateHireLicense(),
)