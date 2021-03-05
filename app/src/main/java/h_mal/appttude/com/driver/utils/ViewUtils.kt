package h_mal.appttude.com.driver.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import h_mal.appttude.com.driver.R

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Context.displayToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Fragment.displayToast(message: String) {
    requireContext().displayToast(message)
}

fun EditText.setEnterPressedListener(action: () -> Unit) {
    setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    action()
                    return@OnEditorActionListener true
            }
            false
    })
}

fun ImageView.setPicassoImage(
        url: String?,
        @DrawableRes placeholderRes: Int = R.drawable.choice_img_round
) {

    val creator = Picasso.get()
        .load(url)
    viewTreeObserver.addOnPreDrawListener {
        creator.resize(width, height)
        true
    }
    creator
        .placeholder(placeholderRes)
        .into(this)
}

fun ImageView.setPicassoImage(
        url: Uri?,
        @DrawableRes placeholderRes: Int = R.drawable.choice_img_round
) {
    val creator = Picasso.get()
        .load(url)
    viewTreeObserver.addOnPreDrawListener {
        creator.resize(width, height)
        true
    }
    creator
        .placeholder(placeholderRes)
        .into(this)
}

fun ViewGroup.generateView(layoutId: Int): View = LayoutInflater
    .from(context)
    .inflate(layoutId, this, false)

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun SearchView.onSubmitListener(searchSubmit: (String) -> Unit) {
    this.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                    searchSubmit.invoke(s)
                    return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                    return true
            }
    })
}

fun Fragment.hideKeyboard() {
    val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun EditText.extractString(): String = text.toString().trim()