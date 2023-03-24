package h_mal.appttude.com.ui.update

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.net.Uri
import androidx.core.widget.doAfterTextChanged
import com.google.firebase.auth.FirebaseUser
import h_mal.appttude.com.base.BaseFragment
import h_mal.appttude.com.databinding.FragmentUpdateProfileBinding
import h_mal.appttude.com.utils.PermissionsUtils.askForPermissions
import h_mal.appttude.com.utils.setEnterPressedListener
import h_mal.appttude.com.utils.setGlideImage
import h_mal.appttude.com.viewmodels.UpdateUserViewModel

const val TAG_CONST = "non-user"
private const val IMAGE_PERMISSION_RESULT = 402

class UpdateProfileFragment : BaseFragment<UpdateUserViewModel, FragmentUpdateProfileBinding>() {

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

        profileImg.setOnClickListener {
            if (askForPermissions(READ_EXTERNAL_STORAGE, IMAGE_PERMISSION_RESULT)) {
                openGalleryForImage()
            }
        }

        submitUpdateProfile.setOnClickListener { submitProfileUpdate() }
    }

    private fun submitProfileUpdate() {
        applyBinding {
            val name: String? = takeIf { nameChangeListener }?.updateName?.text?.toString()
            val imgUri = takeIf { imageChangeListener }?.let { imageUri }

            viewModel.updateProfile(name, imgUri)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) = onPermissionRequest(requestCode, IMAGE_PERMISSION_RESULT, grantResults) {
        openGalleryForImage()
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