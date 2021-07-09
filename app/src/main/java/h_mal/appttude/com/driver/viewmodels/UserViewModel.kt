package h_mal.appttude.com.driver.viewmodels

import com.google.firebase.auth.AuthResult
import h_mal.appttude.com.driver.base.BaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseCompletion
import h_mal.appttude.com.driver.utils.Coroutines.io
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class UserViewModel(
    val auth: FirebaseAuthentication
) : BaseViewModel() {

    fun signInUser(email: String?, password: String?) = io {
        doTryOperation("Failed to sign in") {
            val user: AuthResult = auth.signIn(email!!, password!!).await()
            onSuccess(user)
        }
    }


    fun registerUser(name: String?, email: String?, password: String?) = io {
        doTryOperation("Failed to register user") {
            val user: AuthResult = auth.registerUser(email!!, password!!).await()
            auth.updateProfile(name, null)
            onSuccess(user)
        }
    }


    fun forgotPassword(email: String?) = io {
        doTryOperation("Failed to reset password") {
            val result = auth.forgotPassword(email!!).await()
            onSuccess(result)
        }
    }

    fun splashscreenCheckUserIsLoggedIn() = io {
        delay(1000)
        auth.getUser()?.let {
            onSuccess(it)
        }
        onSuccess(FirebaseCompletion.Default)
    }

}