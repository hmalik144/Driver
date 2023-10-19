package h_mal.appttude.com.driver.base

import android.Manifest
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewbinding.ViewBinding
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.data.ImageDocumentFile
import h_mal.appttude.com.driver.data.ImageResults
import h_mal.appttude.com.driver.ui.permission.PermissionsDeclarationDialog
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.RuntimePermissions
import java.util.concurrent.atomic.AtomicReference

@RuntimePermissions
abstract class ImageFormSubmissionFragment<V : DataSubmissionViewModel2<T>, VB : ViewBinding, T : ImageDocument> :
    FormSubmissionFragment<V, VB, T>(), ImageSelectionHelper {

    private val selectedImage: AtomicReference<Uri> = AtomicReference()

    private fun setSelectedImages(image: Uri) {
        selectedImage.set(image)
    }

    fun getSelectedImages(): Uri? {
        return selectedImage.get()
    }

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        if (data is ImageDocumentFile<*>) {
            setImage(data.image.image, data.image.thumbnail)
            setFields(data.document as T)
        }
    }

    open fun setImage(image: StorageReference?, thumbnail: StorageReference?) {}

    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            result?.let { onImageGalleryResult(result) }
        }

    override fun openGalleryForImageSelection() {
        permissionRequest.launch("image/*")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showStorage() {
        openGalleryForImageSelection()
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showRationaleForStorage(request: PermissionRequest) {
        PermissionsDeclarationDialog(requireContext()).showDialog({
            request.proceed()
        }, {
            request.cancel()
        })
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun onStorageDenied() {
        showToast("Storage permissions have been denied")
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun onStorageNeverAskAgain() {
        showToast("Storage permissions have been to never ask again")
    }

    /**
     *  Called on the result of image selection
     */
    open fun onImageGalleryResult(imageUri: Uri) {
        setSelectedImages(imageUri)
    }

    override fun submitDocument() {
        viewModel.postDataToDatabase(getSelectedImages(), model)
    }
}