package h_mal.appttude.com.driver.viewmodels

import android.net.Uri
import h_mal.appttude.com.driver.base.BaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseCompletion
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.utils.Coroutines.io
import kotlinx.coroutines.tasks.await

class UpdateUserViewModel(
    private val auth: FirebaseAuthentication,
    private val storage: FirebaseStorageSource
) : BaseViewModel() {

    fun updateEmail(oldEmail: String, password: String, newEmail: String) = io {
        doTryOperation("Failed to update email") {
            auth.reauthenticate(oldEmail, password).await()
            auth.updateEmail(newEmail).await()
            postResult("Email address")
        }
    }

    fun updatePassword(oldEmail: String, password: String, newPassword: String) = io {
        doTryOperation("Failed to update password") {
            auth.reauthenticate(oldEmail, password).await()
            auth.updatePassword(newPassword).await()
            postResult("Password")
        }
    }

    fun updateProfile(name: String?, localImageUri: Uri?) = io {
        if (name.isNullOrBlank() && localImageUri == null) return@io
        doTryOperation("Failed to update User") {

            val profilePicUrl = localImageUri?.let {
                val uid = auth.getUid() ?: return@doTryOperation
                val storageRef = storage.profileImageStorageRef(uid)
                storage.uploadImage(it, storageRef, "profile_pic")
            }

            auth.updateProfile(name, profilePicUrl).await()
            postResult("Profile updated")
        }
    }

    fun deleteProfile(oldEmail: String, password: String) = io {
        doTryOperation("Failed to delete profile") {
            auth.reauthenticate(oldEmail, password).await()
            val complete = auth.deleteProfile().await()
            complete.let {
                onSuccess(FirebaseCompletion.ProfileDeleted("Profile deleted"))
                return@doTryOperation
            }
        }
    }


    fun getUser() {
        auth.getUser()?.let {
            onSuccess(it)
            return
        }
        onError("No user signed in")
    }

    private fun postResult(section: String) {
        onSuccess(FirebaseCompletion.Changed("$section has been updated"))
        return
    }
}