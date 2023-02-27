package h_mal.appttude.com.utils

import android.widget.EditText
import h_mal.appttude.com.R


object TextValidationUtils {
    /**
     * Validate input of EditText containing email
     * @return email retrieved if valid
     */
    fun EditText.validateEmailEditText(): String? {
        val input = text.toString().trim()
        if (input.isEmpty()) {
            error = context.getString(R.string.error_field_required)
            requestFocus()
            return null
        }
        if (!isEmailValid(input)) {
            error = context.getString(R.string.error_invalid_email)
            requestFocus()
            return null
        }
        return input
    }

    /**
     * Validate input of EditText containing password
     * @return password retrieved if valid
     */
    fun EditText.validatePasswordEditText(): String? {
        val input = text.toString().trim()
        if (input.isEmpty()) {
            error = context.getString(R.string.error_field_required)
            requestFocus()
            return null
        }
        if (!isPasswordValid(input)) {
            error = context.getString(R.string.error_invalid_password)
            requestFocus()
            return null
        }
        return input
    }

    /**
     * Validate input of EditText containing text
     * @return email retrieved if valid
     */
    fun EditText.validateEditText(): String? {
        val input = text.toString().trim()
        if (input.isEmpty()) {
            error = context.getString(R.string.error_field_required)
            requestFocus()
            return null
        }
        return input
    }

    private fun isEmailValid(email: String?): Boolean {
        email?.let {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
        }
        return false
    }

    private fun isPasswordValid(password: String?): Boolean {
        return (password?.length ?: 0) > 6
    }
}