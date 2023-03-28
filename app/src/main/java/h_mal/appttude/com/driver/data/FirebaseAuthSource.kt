package h_mal.appttude.com.driver.data

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import java.io.IOException

class FirebaseAuthSource : FirebaseAuthentication {
    private val auth = FirebaseAuth.getInstance()

    override fun getUid(): String? = auth.uid

    override fun getUser(): FirebaseUser? = auth.currentUser

    override fun signIn(email: String, password: String): Task<AuthResult> =
        auth.signInWithEmailAndPassword(email, password)

    override fun registerUser(email: String, password: String): Task<AuthResult> =
        auth.createUserWithEmailAndPassword(email, password)

    override fun logOut() = auth.signOut()

    override fun forgotPassword(email: String): Task<Void> = auth.sendPasswordResetEmail(email)

    override fun updateProfile(
        name: String?,
        profilePic: Uri?
    ): Task<Void> {
        val profileUpdates = UserProfileChangeRequest.Builder().apply {
            name?.let { setDisplayName(it) }
            profilePic?.let { photoUri = it }
        }.build()
        return getCurrentUser().updateProfile(profileUpdates)
    }

    override fun reauthenticate(
        email: String,
        password: String
    ): Task<Void> {
        val credential = EmailAuthProvider.getCredential(email, password)
        return getCurrentUser().reauthenticate(credential)
    }

    override fun updateEmail(email: String): Task<Void> = getCurrentUser().updateEmail(email)
    override fun updatePassword(password: String): Task<Void> =
        getCurrentUser().updatePassword(password)

    override fun deleteProfile(): Task<Void> = getCurrentUser().delete()


    override fun userStateListener(): FirebaseAuthStateLiveData {
        return FirebaseAuthStateLiveData(auth)
    }

    private fun getCurrentUser(): FirebaseUser {
        return getUser() ?: throw IOException("User not signed in")
    }
}