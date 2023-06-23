package h_mal.appttude.com.driver.tests

import h_mal.appttude.com.driver.ADMIN_EMAIL
import h_mal.appttude.com.driver.DRIVER_EMAIL
import h_mal.appttude.com.driver.FirebaseTest
import h_mal.appttude.com.driver.robots.homeAdmin
import h_mal.appttude.com.driver.robots.login
import h_mal.appttude.com.driver.ui.user.LoginActivity
import org.junit.Test
import java.io.IOException

class UserListTest : FirebaseTest<LoginActivity>(LoginActivity::class.java) {

    @Test
    fun loginAsAdmin_updateDriverIdentifier_loggedIn() {
        login {
            waitFor(1100)
            attemptLogin(ADMIN_EMAIL)
        }
        homeAdmin {
            clickOnDriverIdentifier("rsaif660@gmail.com")
            submitDialog("ID45")
        }
    }

    @Test
    fun loginAsUser_unableToSeeDrivers_loggedIn() {
        login {
            waitFor(1100)
            attemptLogin(DRIVER_EMAIL)
        }
        homeAdmin {
            showNoPermissionsDisplay()
            throw IOException("sadfasdfasdf")
        }
    }

}