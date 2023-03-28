package h_mal.appttude.com.driver.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes

fun View.triggerAnimation(@AnimRes id: Int, complete: (View) -> Unit) {
    val animation = AnimationUtils.loadAnimation(context, id)
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation?) = complete(this@triggerAnimation)
        override fun onAnimationStart(a: Animation?) {}
        override fun onAnimationRepeat(a: Animation?) {}
    })
    startAnimation(animation)
}