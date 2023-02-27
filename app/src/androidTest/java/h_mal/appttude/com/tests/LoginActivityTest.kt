package h_mal.appttude.com.tests


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.google.firebase.auth.FirebaseAuth
import h_mal.appttude.com.BaseUiTest
import h_mal.appttude.com.R
import h_mal.appttude.com.robots.home
import h_mal.appttude.com.ui.user.LoginActivity
import h_mal.appttude.com.robots.login
import h_mal.appttude.com.robots.register
import org.junit.*
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest: BaseUiTest<LoginActivity>() {

    @Ignore
    override fun getApplicationClass() = LoginActivity::class.java

    @After
    fun afterTest(){
        FirebaseAuth.getInstance().signOut()
    }

    @Test
    fun verifyUserLogin_validUsernameAndPassword_loggedIn() {
        login {
            setEmail("test-user@testuserdriver.com")
            setPassword("Password1234")
            clickLogin()
        }
        home {
            checkTitleExists(getResourceString(R.string.welcome_title))
        }
    }

}
