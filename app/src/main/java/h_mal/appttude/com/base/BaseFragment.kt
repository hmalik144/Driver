package h_mal.appttude.com.base

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.viewbinding.ViewBinding
import h_mal.appttude.com.application.ApplicationViewModelFactory
import h_mal.appttude.com.data.ViewState
import h_mal.appttude.com.utils.PermissionsUtils
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

const val IMAGE_SELECT_REQUEST_CODE = 401

abstract class BaseFragment<V : BaseViewModel, VB : ViewBinding>(
) : Fragment(), KodeinAware {

    private var _binding: VB? = null
    private val binding: VB
        get() = _binding ?: error("Must only access binding while fragment is attached.")

    var mActivity: BaseActivity<V, *>? = null

    val viewModel: V by getFragmentViewModel()

    private var multipleImage: Boolean = false

    fun setImageSelectionAsMultiple() {
        multipleImage = true
    }

    override val kodein by kodein()
    val factory by instance<ApplicationViewModelFactory>()

    fun getFragmentViewModel(): Lazy<V> =
        createViewModelLazy(getGenericClassAt(0), { viewModelStore }, factoryProducer = { factory })

    fun LayoutInflater.inflateBindingByType(
        container: ViewGroup?,
        genericClassAt: KClass<VB>
    ): VB = try {
        @Suppress("UNCHECKED_CAST")
        genericClassAt.java.methods.first { inflateFun ->
            inflateFun.parameterTypes.size == 3
                    && inflateFun.parameterTypes.getOrNull(0) == LayoutInflater::class.java
                    && inflateFun.parameterTypes.getOrNull(1) == ViewGroup::class.java
                    && inflateFun.parameterTypes.getOrNull(2) == Boolean::class.java
        }.invoke(null, this, container, false) as VB
    } catch (exception: Exception) {
        throw IllegalStateException("Can not inflate binding from generic")
    }

    @Suppress("UNCHECKED_CAST")
    fun <CLASS : Any> Any.getGenericClassAt(position: Int): KClass<CLASS> =
        ((javaClass.genericSuperclass as? ParameterizedType)
            ?.actualTypeArguments?.getOrNull(position) as? Class<CLASS>)
            ?.kotlin
            ?: throw IllegalStateException("Can not find class from generic argument")

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IMAGE_SELECT_REQUEST_CODE -> {
                    data?.clipData?.convertToList()?.let { clip ->
                        val list = clip.takeIf { it.size > 10 }?.let {
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
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, multipleImage)
        startActivityForResult(intent, IMAGE_SELECT_REQUEST_CODE)
    }

}