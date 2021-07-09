package h_mal.appttude.com.driver.data

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthentication{
    fun getUid(): String?
    fun getUser(): FirebaseUser?
    fun signIn(email: String, password: String): Task<AuthResult>
    fun registerUser(email: String, password: String): Task<AuthResult>
    fun logOut()
    fun forgotPassword(email: String): Task<Void>
    fun updateProfile(
        name: String?,
        profilePic: Uri?
    ): Task<Void>?
    fun reauthenticate(
        email: String,
        password: String
    ): Task<Void>?
    fun updateEmail(email: String): Task<Void>?
    fun updatePassword(password: String): Task<Void>?
    fun deleteProfile(): Task<Void>?
}