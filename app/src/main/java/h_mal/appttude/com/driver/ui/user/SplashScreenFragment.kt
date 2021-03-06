package h_mal.appttude.com.driver.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseUser
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.data.FirebaseCompletion
import h_mal.appttude.com.driver.utils.Coroutines.main
import h_mal.appttude.com.driver.utils.navigateTo
import h_mal.appttude.com.driver.viewmodels.UserViewModel
import kotlinx.coroutines.delay


class SplashScreenFragment : BaseFragment<UserViewModel>() {

    private val userViewModel by activityViewModels<UserViewModel>()
    override fun getViewModel(): UserViewModel = userViewModel
    override fun getLayoutId(): Int = R.layout.fragment_splash_screen

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.splashscreenCheckUserIsLoggedIn()
    }

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        when(data){
            is FirebaseCompletion.Default -> view?.navigateTo(R.id.to_loginFragment)
        }
    }

}