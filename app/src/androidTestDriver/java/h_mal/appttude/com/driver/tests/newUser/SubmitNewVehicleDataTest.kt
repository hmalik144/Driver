package h_mal.appttude.com.driver.tests.newUser


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import h_mal.appttude.com.driver.model.Insurance
import h_mal.appttude.com.driver.model.Logbook
import h_mal.appttude.com.driver.model.Mot
import h_mal.appttude.com.driver.model.PrivateHireVehicle
import h_mal.appttude.com.driver.model.VehicleProfile
import h_mal.appttude.com.driver.robots.*
import h_mal.appttude.com.driver.robots.vehicle.insurance
import h_mal.appttude.com.driver.robots.vehicle.logbook
import h_mal.appttude.com.driver.robots.vehicle.mot
import h_mal.appttude.com.driver.robots.vehicle.privateHireVehicleLicense
import h_mal.appttude.com.driver.robots.vehicle.vehicleProfile
import org.junit.*
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class SubmitNewVehicleDataTest : VehicleProfileTest() {

    @Test
    fun signedInUser_uploadsValidVehicleProfile_uploadSuccessful() {
        vehicleScreen {
            vehicleProfile()
        }
        vehicleProfile {
            val data = getAssetData<VehicleProfile>()
            submitAndValidate(data)
        }
    }

    @Test
    fun signedInUser_uploadsValidInsurance_uploadSuccessful() {
        vehicleScreen {
            insurance()
        }
        insurance {
            val data = getAssetData<Insurance>()
            submitAndValidate(data)
        }
    }

    @Test
    fun signedInUser_uploadsValidMot_uploadSuccessful() {
        vehicleScreen {
            mot()
        }
        mot {
            val data = getAssetData<Mot>()
            submitAndValidate(data)
        }
    }

    @Test
    fun signedInUser_uploadsValidLogbook_uploadSuccessful() {
        vehicleScreen {
            logbook()
        }
        logbook {
            val data = getAssetData<Logbook>()
            submitAndValidate(data)
        }
    }

    @Test
    fun signedInUser_uploadsValidPrivateHireVehicleLicense_uploadSuccessful() {
        vehicleScreen {
            privateHireVehicleLicense()
        }
        privateHireVehicleLicense {
            val data = getAssetData<PrivateHireVehicle>()
            submitAndValidate(data)
        }
    }

}
