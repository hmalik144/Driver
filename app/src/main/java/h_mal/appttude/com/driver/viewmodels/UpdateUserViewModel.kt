package h_mal.appttude.com.driver.viewmodels

import android.net.Uri
import h_mal.appttude.com.driver.base.BaseViewModel
import h_mal.appttude.com.driver.data.FirebaseAuthentication
import h_mal.appttude.com.driver.data.FirebaseCompletion
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.utils.Coroutines.io
import kotlinx.coroutines.tasks.await
import java.io.IOException

class UpdateUserViewModel(
    private val auth: FirebaseAuthentication,
    private val storage: FirebaseStorageSource
) : BaseViewModel() {

    fun updateEmail(oldEmail: String, password: String, newEmail: String) = io {
        doTryOperation("Failed to update email"){
            auth.reauthenticate(oldEmail, password)?.await()
            val complete = auth.updateEmail(newEmail)?.await()
            complete?.postResult("Email address")
        }
    }

    fun updatePassword(oldEmail: String, password: String, newPassword: String) = io {
        doTryOperation("Failed to update password"){
            auth.reauthenticate(oldEmail, password)?.await()
            val complete = auth.updatePassword(newPassword)?.await()
            complete?.postResult("Password")
        }
    }

    fun updateProfile(name: String?, localImageUri: Uri?) = io {
        if (name.isNullOrBlank() && localImageUri == null) return@io
        doTryOperation("Failed to update User"){

            val profilePicUrl = localImageUri?.let {
                val storageRef = storage.profileImageStorageRef(auth.getUid()!!)
                storage.uploadImage(it, storageRef, "profile_pic")
            }

            val complete = auth.updateProfile(name, profilePicUrl)?.await()
            complete.postResult("Profile updated")
        }
    }

    fun deleteProfile(oldEmail: String, password: String) = io {
        doTryOperation("Failed to delete profile"){
            auth.reauthenticate(oldEmail, password)?.await()
            val complete = auth.deleteProfile()?.await()
            complete?.let {
                onSuccess(FirebaseCompletion.ProfileDeleted("Profile deleted"))
                return@doTryOperation
            }
            throw IOException("Failed to complete")

        }
    }


    fun getUser(): Boolean {
        return auth.getUser()?.let {
            onSuccess(it)
            true
        } ?: false
    }

    private fun Void?.postResult(section: String){
        this?.let {
            onSuccess(FirebaseCompletion.Changed("$section has been updated"))
            return
        }
        throw IOException("Failed to complete")
    }
}