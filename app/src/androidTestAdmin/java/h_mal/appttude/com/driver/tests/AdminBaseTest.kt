package h_mal.appttude.com.driver.tests

import h_mal.appttude.com.driver.ADMIN_EMAIL
import h_mal.appttude.com.driver.FirebaseTest
import h_mal.appttude.com.driver.PASSWORD
import h_mal.appttude.com.driver.ui.MainActivity
import kotlinx.coroutines.runBlocking

open class AdminBaseTest: FirebaseTest<MainActivity>(MainActivity::class.java, signOutAfterTest = false) {

    override fun beforeLaunch() {
        runBlocking {
            login(ADMIN_EMAIL, PASSWORD)
        }
    }
}