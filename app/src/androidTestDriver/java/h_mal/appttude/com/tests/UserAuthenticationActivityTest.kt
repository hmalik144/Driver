package h_mal.appttude.com.tests


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import h_mal.appttude.com.FirebaseTest
import h_mal.appttude.com.R
import h_mal.appttude.com.USER_PASSWORD
import h_mal.appttude.com.robots.home
import h_mal.appttude.com.robots.login
import h_mal.appttude.com.robots.register
import h_mal.appttude.com.ui.user.LoginActivity
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
        }
    }

}
