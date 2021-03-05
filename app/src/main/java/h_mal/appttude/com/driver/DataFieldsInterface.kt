package h_mal.appttude.com.driver

import android.net.Uri
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import h_mal.appttude.com.driver.data.DataFieldState
import h_mal.appttude.com.driver.utils.setPicassoImage

interface DataFieldsInterface {

    fun EditText.setFieldFromDataFetch(data: String?) = apply {
        setText(data)
        tag = DataFieldState.NonUserSateUpdated
    }

    fun EditText.applyChangeListener() = doAfterTextChanged {
        if (tag == DataFieldState.NonUserSateUpdated) {
            tag = DataFieldState.DefaultState
            return@doAfterTextChanged
        }
        tag = DataFieldState.UserUpdateState
    }

    fun ImageView.setFieldFromFetchData(data: String?)= apply {
        setPicassoImage(data)
        tag = DataFieldState.NonUserSateUpdated
    }

    fun EditText.getTextAfterFieldValidation(): String? =
        takeIf { it.tag is DataFieldState.UserUpdateState }?.let { it.text?.toString() }

    fun ImageView.getTextAfterFieldValidation(imageUri: Uri?): Uri? =
        takeIf { it.tag is DataFieldState.UserUpdateState }?.let { imageUri }
}