package h_mal.appttude.com.driver.Global

import android.app.Activity


class ViewController constructor(private val activity: Activity?) {
    fun progress(vis: Int) {
        if (activity is ViewControllerInterface) {
            (activity as ViewControllerInterface).progressVisibility(vis)
        }
    }

    fun reloadDrawer() {
        if (activity is ViewControllerInterface) {
            (activity as ViewControllerInterface).updateDrawer()
        }
    }

    open interface ViewControllerInterface {
        fun progressVisibility(vis: Int)
        fun updateDrawer()
    }
}