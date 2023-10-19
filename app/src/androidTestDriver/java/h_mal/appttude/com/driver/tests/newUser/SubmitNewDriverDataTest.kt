package h_mal.appttude.com.driver.tests.newUser


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import h_mal.appttude.com.driver.model.DriverProfile
import h_mal.appttude.com.driver.model.DriversLicense
import h_mal.appttude.com.driver.model.PrivateHireLicense
import h_mal.appttude.com.driver.robots.*
import h_mal.appttude.com.driver.robots.driver.driversLicense
import h_mal.appttude.com.driver.robots.driver.driversProfile
import h_mal.appttude.com.driver.robots.driver.privateHireLicenseRobot
import org.junit.*
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class SubmitNewDriverDataTest : DriverProfileTest() {

    @Test
    fun signedInUser_uploadsValidLicenseDetails_uploadSuccessful() {
        driverScreen {
            driverLicense()
        }
        driversLicense {
            val data = getAssetData<DriversLicense>()
            submitAndValidate(data)

            waitFor(17000)
        }
    }

    @Test
    fun signedInUser_uploadsValidDriverDetails_uploadSuccessful() {
        driverScreen {
            driverProfile()
        }
        driversProfile {
            val data = getAssetData<DriverProfile>()
            submitAndValidate(data)
        }
    }

    @Test
    fun signedInUser_uploadsValidPrivateHireDetails_uploadSuccessful() {
        driverScreen {
            privateHireLicense()
        }
        privateHireLicenseRobot {
            val data = getAssetData<PrivateHireLicense>()
            submitAndValidate(data)
        }
    }

}
