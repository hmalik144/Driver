package h_mal.appttude.com.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import h_mal.appttude.com.application.ApplicationViewModelFactory
import h_mal.appttude.com.R
import h_mal.appttude.com.data.ViewState
import h_mal.appttude.com.espresso.IdlingResourceClass
import h_mal.appttude.com.utils.displayToast
import h_mal.appttude.com.utils.hide
import h_mal.appttude.com.utils.show
import h_mal.appttude.com.utils.triggerAnimation
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


abstract class BaseActivity<V : BaseViewModel> : AppCompatActivity(), KodeinAware {

    private lateinit var loadingView: View

    abstract fun getViewModel(): V?
    abstract val layoutId: Int

    override val kodein by kodein()
    val factory by instance<ApplicationViewModelFactory>()
    private val idlingResource by instance<IdlingResourceClass>()

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
        IdlingResourceClass.increment()
    }

    /**
     *  Called in case of success or some data emitted from the liveData in viewModel
     */
    open fun onSuccess(data: Any?) {
        loadingView.fadeOut()
        loading = false
        IdlingResourceClass.decrement()
    }

    /**
     *  Called in case of failure or some error emitted from the liveData in viewModel
     */
    open fun onFailure(error: String?) {
        error?.let { displayToast(it) }
        loadingView.fadeOut()
        loading = false
        IdlingResourceClass.decrement()
    }

    private fun configureObserver() {
        getViewModel()?.uiState?.observe(this, Observer {
            when (it) {
                is ViewState.HasStarted -> onStarted()
                is ViewState.HasData<*> -> onSuccess(it.data.getContentIfNotHandled())
                is ViewState.HasError -> onFailure(it.error.getContentIfNotHandled())
            }
        })
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

}