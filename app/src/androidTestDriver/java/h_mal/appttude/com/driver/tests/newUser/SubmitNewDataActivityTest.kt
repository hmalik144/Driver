package h_mal.appttude.com.driver.tests.newUser


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.GrantPermissionRule
import h_mal.appttude.com.driver.FirebaseTest
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.robots.*
import h_mal.appttude.com.driver.robots.driver.driversLicense
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
    fun verifyUserRegistration_validUsernameAndPassword_loggedIn() {
        home {
            waitFor(2500)
            checkTitleExists(getResourceString(R.string.welcome_title))
            requestProfile()
            openDriverProfile()
        }
        driverScreen {
            driverLicense()
        }
        driversLicense {
            submitForm("SAMPLE8456310LTU", 2022, 10, 2)

        }

    }

}
