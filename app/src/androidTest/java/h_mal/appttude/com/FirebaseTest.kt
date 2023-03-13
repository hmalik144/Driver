package h_mal.appttude.com

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import h_mal.appttude.com.base.BaseActivity
import h_mal.appttude.com.data.FirebaseAuthSource
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.After
import org.junit.BeforeClass

open class FirebaseTest<T : BaseActivity<*>>(
    activity: Class<T>,
    private val registered: Boolean = false,
    private val signedIn: Boolean = false
) : BaseUiTest<T>(activity) {

    private val firebaseAuthSource by lazy { FirebaseAuthSource() }

    private var email: String? = null

    companion object {
        /**
         *  Setup firebase emulators before all tests
         */
        @JvmStatic
        @BeforeClass
        fun setupFirebase() {
            FirebaseAuth.getInstance().useEmulator("10.0.2.2", 9099)
            FirebaseDatabase.getInstance().useEmulator("10.0.2.2", 9000)
            FirebaseStorage.getInstance().useEmulator("10.0.2.2", 9199)
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
    fun tearDownFirebase() = runBlocking {
        removeUser()
        firebaseAuthSource.logOut()
    }

    suspend fun setupUser(
        signInEmail: String = generateEmailAddress(),
        password: String = USER_PASSWORD
    ) {
        email = signInEmail
        firebaseAuthSource.registerUser(signInEmail, password).await().user
    }

    // remove the user we created for testing
    suspend fun removeUser() {
        getEmail()?.let {
            firebaseAuthSource.reauthenticate(it, USER_PASSWORD).await()
            firebaseAuthSource.deleteProfile().await()
        }
    }

    fun generateEmailAddress(): String {
        val suffix = (1000..50000).random()
        return "test-${suffix}@test-account.com"
    }

    fun getEmail(): String? {
        firebaseAuthSource.getUser()?.email?.let {
            return it
        }
        return email
    }
}