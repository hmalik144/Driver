package h_mal.appttude.com.base

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import h_mal.appttude.com.application.ApplicationViewModelFactory
import h_mal.appttude.com.data.ViewState
import h_mal.appttude.com.utils.PermissionsUtils
import kotlinx.android.synthetic.main.fragment_driver_profile.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

const val IMAGE_SELECT_REQUEST_CODE = 401
abstract class BaseFragment<V : BaseViewModel>(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId), KodeinAware {

    var mActivity: BaseActivity<V>? = null
    abstract fun getViewModel(): V

    private var multipleImage: Boolean = false

    fun setImageSelectionAsMultiple(){
        multipleImage = true
    }

    override val kodein by kodein()
    val factory by instance<ApplicationViewModelFactory>()

    inline fun <reified VM : ViewModel> getFragmentViewModel(): Lazy<VM> = viewModels { factory }

    @Suppress("UNCHECKED_CAST")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as BaseActivity<V>
        configureObserver()
    }

    /**
     *  Called in case of starting operation liveData in viewModel
     */
    open fun onStarted() {
        mActivity?.onStarted()
    }

    /**
     *  Called in case of success or some data emitted from the liveData in viewModel
     */
    open fun onSuccess(data: Any?) {
        mActivity?.onSuccess(data)
    }

    /**
     *  Called in case of failure or some error emitted from the liveData in viewModel
     */
    open fun onFailure(error: String?) {
        mActivity?.onFailure(error)
    }

    private fun configureObserver() {
        getViewModel().uiState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.HasStarted -> onStarted()
                is ViewState.HasData<*> -> onSuccess(it.data.getContentIfNotHandled())
                is ViewState.HasError -> onFailure(it.error.getContentIfNotHandled())
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                IMAGE_SELECT_REQUEST_CODE -> {
                    data?.clipData?.convertToList()?.let { clip ->
                        val list = clip.takeIf { it.size > 10 }?.let{
                            clip.subList(0, 9)
                        } ?: clip
                        onImageGalleryResult(list)
                        return
                    }
                    onImageGalleryResult(data?.data)
                }
            }

        }
    }

    private fun ClipData.convertToList(): List<Uri> = 0.rangeTo(itemCount).map { getItemAt(it).uri }

    /**
     * Pair with {@link #Fragment.onRequestPermissionsResult}
     * @param ourRequestCode
     * @param requestCode
     * checks that ourRequestCode was granted
     * sends callback with
     * @param permissionGranted
     */
    fun onPermissionRequest(
        requestCode: Int, ourRequestCode: Int, grantResults: IntArray,
        permissionGranted: () -> Unit
    ){
        when (requestCode) {
            ourRequestCode -> {
                if (PermissionsUtils.isGranted(grantResults)) {
                    permissionGranted.invoke()
                }
                return
            }
        }
    }

    /**
     *  Called on the result of image selection
     */
    open fun onImageGalleryResult(imageUri: Uri?){ }
    /**
     *  Called on the result of multiple image selection
     */
    open fun onImageGalleryResult(imageUris: List<Uri>?){ }

    fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, multipleImage);
        startActivityForResult(intent, IMAGE_SELECT_REQUEST_CODE)
    }

}