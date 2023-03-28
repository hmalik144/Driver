package h_mal.appttude.com.driver.tests


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import h_mal.appttude.com.driver.FirebaseTest
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.USER_PASSWORD
import h_mal.appttude.com.driver.robots.home
import h_mal.appttude.com.driver.robots.login
import h_mal.appttude.com.driver.ui.user.LoginActivity
import org.junit.*
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class RegisteredUserAuthenticationActivityTest :
    FirebaseTest<LoginActivity>(LoginActivity::class.java, registered = true, signedIn = false) {

    @Test
    fun verifyUserLogin_validUsernameAndPassword_loggedIn() {
        login {
            waitFor(1100)
            setEmail(getEmail())
            setPassword(USER_PASSWORD)
            clickLogin()
        }
        home {
            checkTitleExists(getResourceString(R.string.welcome_title))
        }
    }
}
