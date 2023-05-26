package h_mal.appttude.com.driver.base

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.core.view.children
import androidx.viewbinding.ViewBinding
import h_mal.appttude.com.driver.data.USER_CONST
import h_mal.appttude.com.driver.utils.GenericsHelper.getGenericClassAt
import h_mal.appttude.com.driver.utils.hide
import h_mal.appttude.com.driver.utils.isTrue

open class DataViewerFragment<V : DataViewerBaseViewModel<T>, VB : ViewBinding, T : Any> :
    BaseFragment<V, VB>() {

    override fun setupView(binding: VB) {
        super.setupView(binding)

        (binding.root as ViewGroup).children.forEach { disableViews(it) }
    }

    private fun disableViews(view: View) {
        if (view is EditText)
            view.isFocusable = false
        if (view is CheckBox)
            view.isFocusable = false
        else if (view is ViewGroup)
            view.children.forEach { disableViews(it) }
    }

    override fun onStart() {
        super.onStart()
        requireArguments().getString(USER_CONST)?.let {
            viewModel.initViewModel(it)
        }
    }


    @Suppress("UNCHECKED_CAST")
    override fun onSuccess(data: Any?) {
        super.onSuccess(data)

        data?.let { (data::class == getGenericClassAt<T>(2)) }?.isTrue {
            setFields(data as T)
        }
    }

    open fun setFields(data: T) {}

    fun viewsToHide(vararg view: View) {
        view.forEach { it.hide() }
    }

}