package h_mal.appttude.com.driver.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.viewbinding.ViewBinding
import h_mal.appttude.com.driver.data.UserAuthState
import h_mal.appttude.com.driver.ui.user.LoginActivity
import h_mal.appttude.com.driver.utils.GenericsHelper.getGenericClassAt
import h_mal.appttude.com.driver.utils.TextValidationUtils.validateEditText
import kotlin.reflect.full.createInstance

abstract class FormSubmissionFragment<V : DataSubmissionViewModel<T>, VB : ViewBinding, T : Document> :
    BaseFragment<V, VB>() {

    var model: T = getGenericClassAt<T>(2).createInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // If user is logged out then navigate to LoginActivity
        viewModel.stateLiveData.observe(viewLifecycleOwner) {
            if (it is UserAuthState.LoggedOut) {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                requireActivity().finish()
                return@observe
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onSuccess(data: Any?) {
        super.onSuccess(data)

        when (data) {
            is Model -> {
                model = data as T
                setFields(data)
            }
        }
    }

    open fun setFields(data: T) { }

    open fun submit() {}

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

    open fun submitDocument() {
        viewModel.postDataToDatabase(model)
    }
}