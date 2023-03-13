package h_mal.appttude.com.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.IdlingResource
import h_mal.appttude.com.R
import h_mal.appttude.com.application.ApplicationViewModelFactory
import h_mal.appttude.com.data.ViewState
import h_mal.appttude.com.utils.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


abstract class BaseActivity<V : BaseViewModel> : AppCompatActivity(), KodeinAware {
    // The Idling Resource which will be null in production.
    private var mIdlingResource: BasicIdlingResource? = null
    private lateinit var loadingView: View

    abstract fun getViewModel(): V?
    abstract val layoutId: Int

    override val kodein by kodein()
    val factory by instance<ApplicationViewModelFactory>()

    inline fun <reified VM : ViewModel> createLazyViewModel(): Lazy<VM> = viewModels { factory }
    inline fun <reified VM : ViewModel> createViewModel(): VM =
        ViewModelProvider(viewModelStore, factory).get(VM::class.java)


    private var loading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureObserver()
        setContentView(layoutId)
    }

    /**
     *  Creates a loading view which to be shown during async operations
     *
     *  #setOnClickListener(null) is an ugly work around to prevent under being clicked during
     *  loading
     */
    private fun instantiateLoadingView(){
//        loadingView = View.inflate(this, R.layout.progress_layout, null)
        loadingView = layoutInflater.inflate(R.layout.progress_layout, null)
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
        loading = true
        mIdlingResource?.setIdleState(false)
    }

    /**
     *  Called in case of success or some data emitted from the liveData in viewModel
     */
    open fun onSuccess(data: Any?) {
        loadingView.fadeOut()
        loading = false
        mIdlingResource?.setIdleState(true)
    }

    /**
     *  Called in case of failure or some error emitted from the liveData in viewModel
     */
    open fun onFailure(error: String?) {
        error?.let { displayToast(it) }
        loadingView.fadeOut()
        loading = false
        mIdlingResource?.setIdleState(true)
    }

    private fun configureObserver() {
        getViewModel()?.uiState?.observe(this) {
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
        if (!loading) super.onBackPressed()
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