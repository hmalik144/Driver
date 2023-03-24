package h_mal.appttude.com.tests


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import h_mal.appttude.com.FirebaseTest
import h_mal.appttude.com.R
import h_mal.appttude.com.USER_PASSWORD
import h_mal.appttude.com.robots.home
import h_mal.appttude.com.robots.login
import h_mal.appttude.com.ui.user.LoginActivity
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
