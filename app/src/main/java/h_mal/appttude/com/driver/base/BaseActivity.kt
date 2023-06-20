package h_mal.appttude.com.driver.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.inflate
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelLazy
import androidx.test.espresso.IdlingResource
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback
import com.google.android.material.snackbar.Snackbar
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.application.ApplicationViewModelFactory
import h_mal.appttude.com.driver.data.ViewState
import h_mal.appttude.com.driver.utils.BasicIdlingResource
import h_mal.appttude.com.driver.utils.GenericsHelper.getGenericClassAt
import h_mal.appttude.com.driver.utils.GenericsHelper.inflateBindingByType
import h_mal.appttude.com.driver.utils.hide
import h_mal.appttude.com.driver.utils.show
import h_mal.appttude.com.driver.utils.triggerAnimation
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

abstract class BaseActivity<V : BaseViewModel, VB : ViewBinding> : AppCompatActivity(),
    KodeinAware {
    // The Idling Resource which will be null in production.
    private var mIdlingResource: BasicIdlingResource? = null
    private lateinit var loadingView: View

    private var _binding: VB? = null
    val binding: VB
        get() = _binding ?: error("Must only access binding while fragment is attached.")

    val viewModel: V by createLazyViewModel()

    override val kodein by kodein()
    private val factory by instance<ApplicationViewModelFactory>()

    /**
     * Create a lazy viewmodel based on the generic view model [V].
     */
    private fun createLazyViewModel(): Lazy<V> = ViewModelLazy(
        getGenericClassAt(0),
        { viewModelStore },
        { factory },
        { defaultViewModelCreationExtras }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureObserver()
        _binding = inflateBindingByType(getGenericClassAt(1), layoutInflater)
        setContentView(requireNotNull(_binding).root)
        setupView(binding)
    }


    open fun setupView(binding: VB) {}

    /**
     * Extension function which can be used in our child class for binding.
     *
     * @sample applyBinding { textView.text = "Hello" }
     */
    fun applyBinding(block: VB.() -> Unit) {
        block(binding)
    }

    /**
     *  Creates a loading view which to be shown during async operations
     *
     *  #setOnClickListener(null) is an ugly work around to prevent under being clicked during
     *  loading
     */
    private fun instantiateLoadingView() {
        loadingView = inflate(this, R.layout.progress_layout, null)
        loadingView.setOnClickListener(null)
        addContentView(loadingView, LayoutParams(MATCH_PARENT, MATCH_PARENT))
        loadingView.hide()
    }

    override fun onStart() {
        super.onStart()
        instantiateLoadingView()
    }

    fun <A : AppCompatActivity> startActivity(activity: Class<A>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    /**
     *  Called in case of success or some data emitted from the liveData in viewModel
     */
    open fun onStarted() {
        loadingView.fadeIn()
        mIdlingResource?.setIdleState(false)
    }

    /**
     *  Called in case of success or some data emitted from the liveData in viewModel
     */
    open fun onSuccess(data: Any?) {
        loadingView.fadeOut()
        mIdlingResource?.setIdleState(true)
    }

    /**
     *  Called in case of failure or some error emitted from the liveData in viewModel
     */
    open fun onFailure(error: String?) {
        error?.let { showToast(it) }
        loadingView.fadeOut()
        mIdlingResource?.setIdleState(true)
    }

    private fun configureObserver() {
        viewModel.uiState.observe(this) {
            when (it) {
                is ViewState.HasStarted -> onStarted()
                is ViewState.HasData<*> -> onSuccess(it.data.getContentIfNotHandled())
                is ViewState.HasError -> onFailure(it.error.getContentIfNotHandled())
            }
        }
    }

    private fun View.fadeIn() = apply {
        show()
        triggerAnimation(R.anim.nav_default_enter_anim) {}
    }

    private fun View.fadeOut() = apply {
        hide()
        triggerAnimation(R.anim.nav_default_exit_anim) {}
    }


    override fun onBackPressed() {
        loadingView.hide()
        super.onBackPressed()
    }

    fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            toast.addCallback(object : Toast.Callback() {
                override fun onToastHidden() {
                    super.onToastHidden()
                    mIdlingResource?.setIdleState(true)
                }
                override fun onToastShown() {
                    super.onToastShown()
                    mIdlingResource?.setIdleState(false)
                }
            })
        }
        toast.show()
    }

    fun showSnackBar(message: String) {
        val snackbar = Snackbar.make(
            window.decorView.findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        )
        snackbar.addCallback(object : BaseCallback<Snackbar>() {
            override fun onShown(transientBottomBar: Snackbar?) {
                super.onShown(transientBottomBar)
                mIdlingResource?.setIdleState(false)
            }
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                mIdlingResource?.setIdleState(true)
            }
        })
        snackbar.show()
    }

    /**
     * Only called from test, creates and returns a new [BasicIdlingResource].
     */
    @VisibleForTesting
    fun getIdlingResource(): IdlingResource? {
        if (mIdlingResource == null) {
            mIdlingResource = BasicIdlingResource()
        }
        return mIdlingResource
    }
}