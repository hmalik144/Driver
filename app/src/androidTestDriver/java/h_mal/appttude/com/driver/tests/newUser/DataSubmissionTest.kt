package h_mal.appttude.com.driver.tests.newUser

import androidx.test.espresso.matcher.ViewMatchers.withText
import h_mal.appttude.com.driver.FirebaseTest
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.helpers.EspressoHelper.trying
import h_mal.appttude.com.driver.helpers.EspressoHelper.waitForView
import h_mal.appttude.com.driver.model.DriverProfile
import h_mal.appttude.com.driver.model.DriversLicense
import h_mal.appttude.com.driver.model.Insurance
import h_mal.appttude.com.driver.model.Logbook
import h_mal.appttude.com.driver.base.Model
import h_mal.appttude.com.driver.model.Mot
import h_mal.appttude.com.driver.model.PrivateHireLicense
import h_mal.appttude.com.driver.model.PrivateHireVehicle
import h_mal.appttude.com.driver.model.VehicleProfile
import h_mal.appttude.com.driver.robots.home
import h_mal.appttude.com.driver.ui.MainActivity
import java.io.IOException

open class DataSubmissionTest :
    FirebaseTest<MainActivity>(MainActivity::class.java, registered = true, signedIn = true) {


    override fun afterLaunch() {
        super.afterLaunch()
        home {
            waitForView(withText(getResourceString(R.string.welcome_title)), waitMillis = 10000)
            trying {
                requestProfile()
            }
        }
    }

    inline fun <reified T : Model> getAssetData(): T {
        val file = when (T::class) {
            DriverProfile::class -> "driver_details"
            DriversLicense::class -> "drivers_license"
            Insurance::class -> "insurance_details"
            Logbook::class -> "log_book"
            Mot::class -> "mot_details"
            PrivateHireLicense::class -> "private_hire_license"
            PrivateHireVehicle::class -> "private_hire_vehicle"
            VehicleProfile::class -> "vehicle_details"
            else -> {
                throw IOException("No file for ${T::class}")
            }
        }
        return readDataFromAsset(file)
    }

}