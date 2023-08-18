package h_mal.appttude.com.driver.ui.update

import android.net.Uri
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.FirebaseUser
import h_mal.appttude.com.driver.base.ImageSelectorFragment
import h_mal.appttude.com.driver.databinding.FragmentUpdateProfileBinding
import h_mal.appttude.com.driver.utils.setEnterPressedListener
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.UpdateUserViewModel

const val TAG_CONST = "non-user"

class UpdateProfileFragment :
    ImageSelectorFragment<UpdateUserViewModel, FragmentUpdateProfileBinding>() {

    private var imageChangeListener: Boolean = false
    private var nameChangeListener: Boolean = false

    private var imageUri: Uri? = null

    override fun setupView(binding: FragmentUpdateProfileBinding) = binding.run {
        viewModel.getUser()

        updateName.apply {
            doAfterTextChanged {
                if (tag == TAG_CONST) {
                    tag = null
                    return@doAfterTextChanged
                }
                nameChangeListener = true
            }
            setEnterPressedListener { submitProfileUpdate() }
        }

        profileImg.setOnClickListener { openGalleryForImage() }

        submit.setOnClickListener { submitProfileUpdate() }
    }

    private fun submitProfileUpdate() {
        applyBinding {
            val name: String? = takeIf { nameChangeListener }?.updateName?.text?.toString()
            val imgUri = takeIf { imageChangeListener }?.let { imageUri }

            viewModel.updateProfile(name, imgUri)
        }
    }

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        if (data is FirebaseUser) setFields(data)
    }

    private fun setFields(firebaseUser: FirebaseUser) {
        applyBinding {
            profileImg.setGlideImage(firebaseUser.photoUrl)
            updateName.apply {
                setText(firebaseUser.displayName)
                tag = TAG_CONST
            }
        }

    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        this.imageUri = imageUri
        applyBinding {
            profileImg.setGlideImage(imageUri)
            imageChangeListener = true
        }

    }

}