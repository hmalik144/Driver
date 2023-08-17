package h_mal.appttude.com.driver.base

import android.Manifest
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import androidx.viewbinding.ViewBinding
import h_mal.appttude.com.driver.ui.permission.PermissionsDeclarationDialog
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
open class ImageSelectorFragment<V : BaseViewModel, VB : ViewBinding> : BaseFragment<V, VB>() {
    private var multipleImage: Boolean = false
    var picUri: Uri? = null

    fun setImageSelectionAsMultiple() {
        multipleImage = true
    }

    fun openGalleryForImage() {
        permissionRequest.launch(multipleImage)
    }

    private val permissionRequest = registerForActivityResult(getResultsContract()) { result ->
        @Suppress("UNCHECKED_CAST")
        when (result) {
            is Uri -> onImageGalleryResult(result)
            is List<*> -> onImageGalleryResult(result as List<Uri>)
        }
    }

    private fun getResultsContract(): ActivityResultContract<Boolean, Any?> {
        return object : ActivityResultContract<Boolean, Any?>() {
            override fun createIntent(context: Context, input: Boolean): Intent {
                return Intent(Intent.ACTION_GET_CONTENT)
                    .addCategory(Intent.CATEGORY_OPENABLE)
                    .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, input)
                    .setType("image/*")
            }

            override fun parseResult(resultCode: Int, intent: Intent?): Any? {
                intent?.clipData?.takeIf { it.itemCount > 1 }?.convertToList()?.let { clip ->
                    val list = clip.takeIf { it.size > 10 }?.let {
                        clip.subList(0, 9)
                    } ?: clip
                    return list
                }
                return intent?.data
            }
        }
    }

    private fun ClipData.convertToList(): List<Uri> = 0.rangeTo(itemCount).map { getItemAt(it).uri }

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
        openGalleryForImage()
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
    open fun onImageGalleryResult(imageUri: Uri?) {
        picUri = imageUri
    }

    /**
     *  Called on the result of multiple image selection
     */
    open fun onImageGalleryResult(imageUris: List<Uri>?) {}
}