package h_mal.appttude.com.driver.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.application.ApplicationViewModelFactory
import h_mal.appttude.com.driver.data.ViewState
import h_mal.appttude.com.driver.utils.displayToast
import h_mal.appttude.com.driver.utils.hide
import h_mal.appttude.com.driver.utils.show
import h_mal.appttude.com.driver.utils.triggerAnimation
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


abstract class BaseActivity<V : BaseViewModel> : AppCompatActivity(), KodeinAware {

    private lateinit var loadingView: View
    abstract fun getViewModel(): V?

    abstract val layoutId: Int

    override val kodein by kodein()
    val factory by instance<ApplicationViewModelFactory>()

    inline fun <reified VM : ViewModel> createLazyViewModel(): Lazy<VM> = viewModels { factory }
    inline fun <reified VM : ViewModel> createViewModel(): VM = ViewModelProvider(viewModelStore, factory).get(VM::class.java)

    var mProgressDialog: View? = null

    private var loading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureObserver()
        setContentView(layoutId)
    }

    override fun onStart() {
        super.onStart()

        loadingView = layoutInflater.inflate(R.layout.progress_layout, null)
        loadingView.setOnClickListener(null)
        addContentView(loadingView, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ))

        loadingView.hide()
    }

    fun <A : AppCompatActivity> startActivity(activity: Class<A>){
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    /**
     *  Called in case of success or some data emitted from the liveData in viewModel
     */
    open fun onStarted() {
        loadingView.fadeIn()
        loading = true
    }

    /**
     *  Called in case of success or some data emitted from the liveData in viewModel
     */
    open fun onSuccess(data: Any?) {
        loadingView.fadeOut()
        loading = false
    }

    /**
     *  Called in case of failure or some error emitted from the liveData in viewModel
     */
    open fun onFailure(error: String?) {
        error?.let { displayToast(it) }
        loadingView.fadeOut()
        loading = false
    }

    private fun configureObserver() {
        getViewModel()?.uiState?.observe(this, Observer {
            when(it){
                is ViewState.HasStarted -> onStarted()
                is ViewState.HasData<*> -> onSuccess(it.data.getContentIfNotHandled())
                is ViewState.HasError -> onFailure(it.error.getContentIfNotHandled())
            }
        })
    }

    private fun View.fadeIn() {
        apply {
            show()
            triggerAnimation(R.anim.nav_default_enter_anim){}
        }
    }

    private fun View.fadeOut() {
        apply {
            hide()
            triggerAnimation(R.anim.nav_default_exit_anim){}
        }
    }

    override fun onBackPressed() {
        if (!loading) super.onBackPressed()
    }

}