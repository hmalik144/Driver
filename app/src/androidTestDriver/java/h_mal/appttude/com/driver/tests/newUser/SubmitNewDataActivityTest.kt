package h_mal.appttude.com.driver.tests.newUser


import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import h_mal.appttude.com.driver.FirebaseTest
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.model.Date
import h_mal.appttude.com.driver.robots.*
import h_mal.appttude.com.driver.robots.driver.driversLicense
import h_mal.appttude.com.driver.robots.driver.driversProfile
import h_mal.appttude.com.driver.robots.driver.privateHireLicenseRobot
import h_mal.appttude.com.driver.ui.MainActivity
import org.junit.*
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class SubmitNewDataActivityTest :
    FirebaseTest<MainActivity>(MainActivity::class.java, registered = true, signedIn = true) {

    @get:Rule
    var permissionRule =
        GrantPermissionRule.grant(android.Manifest.permission.READ_EXTERNAL_STORAGE)

    @Test
    fun newUser_submitDriversDocuments_documentsSubmitted() {
        home {
            waitFor(2500)
            checkTitleExists(getResourceString(R.string.welcome_title))
            requestProfile()
            openDriverProfile()
        }
        // Submit drivers license
        driverScreen {
            driverLicense()
        }
        driversLicense {
            validateEmptyPage()
            waitFor(5000)
            submitForm("SAMPLE8456310LTU", 2022, 10, 2)
            // Todo: validate successful submission
            Espresso.pressBack()
        }
        // Submit drivers profile
        driverScreen {
            driverProfile()
        }
        driversProfile {
            // todo: validate empty page
            submitForm(
                name = "Basic Driver",
                address = "123A Random Street, Suburb, County",
                postcode = "AB12 3CD",
                dob = Date(12, 12, 1989),
                niNumber = "AB123456C",
                firstDateAvailable = Date.now()
            )
            // Todo: validate successful submission
            Espresso.pressBack()
        }
        driverScreen {
            privateHireLicense()
        }
        privateHireLicenseRobot {
            // todo: validate empty page
            submitForm("SAMPLE8456310LTU", 2022, 10, 2)
            // Todo: validate successful submission
            Espresso.pressBack()
        }
    }

}
