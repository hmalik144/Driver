package h_mal.appttude.com.driver.base

import android.Manifest
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewbinding.ViewBinding
import h_mal.appttude.com.driver.data.ImageCollection
import h_mal.appttude.com.driver.ui.permission.PermissionsDeclarationDialog
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
abstract class MultiImageFormSubmissionFragment3<V : DataSubmissionViewModel3<T>, VB : ViewBinding, T : MultiImageDocument> :
    FormSubmissionFragment<V, VB, T>(), ImageSelectionHelper {

    private val selectedImages: MutableList<Uri> = mutableListOf()

    private fun setSelectedImages(images: List<Uri>) {
        selectedImages.clear()
        selectedImages.addAll(images)
    }

    fun getSelectedImages(): List<Uri> {
        return selectedImages
    }

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        if (data is ImageCollection) {
            setImages(data)
        }
    }

    open fun setImages(collection: ImageCollection) {}

    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { result ->
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
    open fun onImageGalleryResult(imageUris: List<Uri>) {
        setSelectedImages(imageUris)
    }

    override fun submitDocument() {
        viewModel.postDataToDatabase(getSelectedImages(), model)
    }

}