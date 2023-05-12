package h_mal.appttude.com.driver.tests


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import h_mal.appttude.com.driver.FirebaseTest
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.USER_PASSWORD
import h_mal.appttude.com.driver.robots.home
import h_mal.appttude.com.driver.robots.login
import h_mal.appttude.com.driver.robots.register
import h_mal.appttude.com.driver.ui.user.LoginActivity
import org.junit.*
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class UserAuthenticationActivityTest : FirebaseTest<LoginActivity>(LoginActivity::class.java) {

    @Test
    fun verifyUserRegistration_validUsernameAndPassword_loggedIn() {
        login {
            waitFor(1100)
            clickRegister()
        }
        register {
            setName("Test User")
            setEmail(generateEmailAddress())
            setPassword(USER_PASSWORD)
            setPasswordConfirm(USER_PASSWORD)
            clickLogin()
        }
        home {
            checkTitleExists(getResourceString(R.string.welcome_title))
            updateProfile()
        }

        // TODO: update user details
    }

}
