package h_mal.appttude.com.driver.base

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.viewbinding.ViewBinding
import h_mal.appttude.com.driver.application.ApplicationViewModelFactory
import h_mal.appttude.com.driver.data.ViewState
import h_mal.appttude.com.driver.utils.GenericsHelper.getGenericClassAt
import h_mal.appttude.com.driver.utils.GenericsHelper.inflateBindingByType
import h_mal.appttude.com.driver.utils.PermissionsUtils
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

abstract class BaseFragment<V : BaseViewModel, VB : ViewBinding> : Fragment(), KodeinAware {

    private var _binding: VB? = null
    private val binding: VB
        get() = _binding ?: error("Must only access binding while fragment is attached.")

    var mActivity: BaseActivity<V, *>? = null

    override val kodein by kodein()
    private val factory by instance<ApplicationViewModelFactory>()

    val viewModel: V by getFragmentViewModel()
    private fun getFragmentViewModel(): Lazy<V> =
        createViewModelLazy(getGenericClassAt(0), { viewModelStore }, factoryProducer = { factory })

    private var multipleImage: Boolean = false

    fun setImageSelectionAsMultiple() {
        multipleImage = true
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflater.inflateBindingByType(container, getGenericClassAt(1))
        return requireNotNull(_binding).root
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as BaseActivity<V, *>
        configureObserver()
        setupView(binding)
    }

    open fun setupView(binding: VB) {}

    fun applyBinding(block: VB.() -> Unit) {
        block(binding)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.HasStarted -> onStarted()
                is ViewState.HasData<*> -> onSuccess(it.data.getContentIfNotHandled())
                is ViewState.HasError -> onFailure(it.error.getContentIfNotHandled())
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
    ) {
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
    open fun onImageGalleryResult(imageUri: Uri?) {}

    /**
     *  Called on the result of multiple image selection
     */
    open fun onImageGalleryResult(imageUris: List<Uri>?) {}

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
                return Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, input)
                }
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
}