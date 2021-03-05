package h_mal.appttude.com.driver.update

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseUser
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.utils.PermissionsUtils.askForPermissions
import h_mal.appttude.com.driver.utils.setEnterPressedListener
import h_mal.appttude.com.driver.utils.setPicassoImage
import h_mal.appttude.com.driver.viewmodels.UpdateUserViewModel
import kotlinx.android.synthetic.main.fragment_update_profile.*

const val TAG_CONST = "non-user"
private const val IMAGE_PERMISSION_RESULT = 402
class UpdateProfileFragment : BaseFragment<UpdateUserViewModel>() {

    private val viewmodel: UpdateUserViewModel by activityViewModels()
    override fun getViewModel(): UpdateUserViewModel = viewmodel
    override fun getLayoutId(): Int = R.layout.fragment_update_profile

    private var imageChangeListener: Boolean = false
    private var nameChangeListener: Boolean = false

    private var imageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.getUser()

        update_name.apply {
            doAfterTextChanged {
                if (tag == TAG_CONST) {
                    tag = null
                    return@doAfterTextChanged
                }
                nameChangeListener = true
            }
            setEnterPressedListener { submitProfileUpdate() }
        }

        profile_img.setOnClickListener {
            if (askForPermissions(READ_EXTERNAL_STORAGE, IMAGE_PERMISSION_RESULT)) {
                openGalleryForImage()
            }
        }

        submit_update_profile.setOnClickListener { submitProfileUpdate() }
    }

    private fun submitProfileUpdate() {
        val name: String? = takeIf { nameChangeListener }?.update_name?.text?.toString()
        val imgUri = takeIf { imageChangeListener }?.let { imageUri }

        viewmodel.updateProfile(name, imgUri)
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
        profile_img.setPicassoImage(firebaseUser.photoUrl)
        update_name.apply {
            setText(firebaseUser.displayName)
            tag = TAG_CONST
        }
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        this.imageUri = imageUri
        profile_img.setImageURI(imageUri)
        imageChangeListener = true
    }

}