package h_mal.appttude.com.driver

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import h_mal.appttude.com.driver.base.BaseActivity
import h_mal.appttude.com.driver.data.FirebaseAuthSource
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.After
import org.junit.BeforeClass

open class FirebaseTest<T : BaseActivity<*, *>>(
    activity: Class<T>,
    private val registered: Boolean = false,
    private val signedIn: Boolean = false,
    private val signOutAfterTest: Boolean = true
) : BaseUiTest<T>(activity) {

    private val firebaseAuthSource by lazy { FirebaseAuthSource() }
    private val firebaseDatabaseSource by lazy { FirebaseDatabaseSource() }
    private val firebaseStorageSource by lazy { FirebaseStorageSource() }

    private var email: String? = null

    companion object {
        /**
         *  Setup firebase emulators before all tests
         */
        @JvmStatic
        @BeforeClass
        fun setupFirebase() {
            val localHost = "10.0.2.2"

            FirebaseAuth.getInstance().useEmulator(localHost, 9099)
            FirebaseDatabase.getInstance().useEmulator(localHost, 9000)
            FirebaseStorage.getInstance().useEmulator(localHost, 9199)
        }
    }

    override fun beforeLaunch() {
        if (registered) {
            runBlocking {
                setupUser()
            }
            if (!signedIn) firebaseAuthSource.logOut()
        }
    }

    @After
    fun tearDownFirebase() {
        if (signOutAfterTest) {
            firebaseAuthSource.logOut()
        }
    }

    suspend fun setupUser(
        signInEmail: String = generateEmailAddress(),
        password: String = USER_PASSWORD
    ) {
        email = signInEmail
        firebaseAuthSource.registerUser(signInEmail, password).await().user
    }

    suspend fun login(
        signInEmail: String,
        password: String
    ) {
        email = signInEmail
        firebaseAuthSource.signIn(signInEmail, password).await()
    }

    // remove the user we created for testing
    suspend fun removeUser() {
        try {
            getEmail()?.let {
                if (firebaseAuthSource.getUser() == null) firebaseAuthSource.signIn(
                    email = it,
                    password = USER_PASSWORD
                ).await()
                firebaseAuthSource.reauthenticate(it, USER_PASSWORD).await()
                firebaseAuthSource.deleteProfile().await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun generateEmailAddress(): String {
        val suffix = (1000..50000).random()
        email = "test-${suffix}@test-account.com"
        return email!!
    }

    fun getEmail(): String? {
        return firebaseAuthSource.getUser()?.email ?: email
    }
}