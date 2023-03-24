package h_mal.appttude.com.base

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.viewbinding.ViewBinding
import h_mal.appttude.com.data.UserAuthState
import h_mal.appttude.com.ui.user.LoginActivity
import h_mal.appttude.com.utils.PermissionsUtils.askForPermissions
import h_mal.appttude.com.utils.TextValidationUtils.validateEditText

private const val IMAGE_PERMISSION_RESULT = 402

abstract class DataSubmissionBaseFragment<V : DataSubmissionBaseViewModel<T>, VB : ViewBinding, T : Any> :
    BaseFragment<V, VB>() {

    var picUri: Uri? = null

    abstract var model: T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.stateLiveData.observe(viewLifecycleOwner) {
            if (it is UserAuthState.LoggedOut) {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        viewModel.getDataFromDatabase()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onSuccess(data: Any?) {
        super.onSuccess(data)

        data?.let {
            if (it::class.java == model::class.java)
                setFields(data as T)
        }
    }

    open fun setFields(data: T) {
        model = data
    }

    open fun submit() {}

    fun openGalleryWithPermissionRequest() {
        if (askForPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, IMAGE_PERMISSION_RESULT)) {
            openGalleryForImage()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) = onPermissionRequest(requestCode, IMAGE_PERMISSION_RESULT, grantResults) {
        openGalleryForImage()
    }

    fun validateEditTexts(vararg editTexts: EditText): Boolean {
        editTexts.forEach {
            if (it.text.isNullOrBlank()) {
                it.validateEditText()
                return false
            }
        }
        return true
    }

    fun EditText.setTextOnChange(output: (m: String) -> Unit) {
        doAfterTextChanged {
            output(text.toString())
        }
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        picUri = imageUri
    }
}