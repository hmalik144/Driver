package h_mal.appttude.com.driver.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.viewbinding.ViewBinding
import h_mal.appttude.com.driver.data.UserAuthState
import h_mal.appttude.com.driver.model.Model
import h_mal.appttude.com.driver.ui.user.LoginActivity
import h_mal.appttude.com.driver.utils.GenericsHelper.getGenericClassAt
import h_mal.appttude.com.driver.utils.TextValidationUtils.validateEditText
import kotlin.reflect.full.createInstance

abstract class DataSubmissionBaseFragment<V : DataSubmissionBaseViewModel<T>, VB : ViewBinding, T : Model> :
    ImageSelectorFragment<V, VB>() {

    var model: T = getGenericClassAt<T>(2).createInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.stateLiveData.observe(viewLifecycleOwner) {
            if (it is UserAuthState.LoggedOut) {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        viewModel.getDataFromDatabase()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onSuccess(data: Any?) {
        super.onSuccess(data)

        data?.let {
            if (it::class.java == model::class.java)
                setFields(data as T)
        }
    }

    open fun setFields(data: T) {
        model = data
    }

    open fun submit() {}

    fun openGalleryWithPermissionRequest() {
        showStorageWithPermissionCheck()
    }

    fun validateEditTexts(vararg editTexts: EditText): Boolean {
        editTexts.forEach {
            if (it.text.isNullOrBlank()) {
                it.validateEditText()
                return false
            }
        }
        return true
    }

    fun EditText.setTextOnChange(output: (m: String) -> Unit) {
        doAfterTextChanged {
            output(text.toString())
        }
    }

}