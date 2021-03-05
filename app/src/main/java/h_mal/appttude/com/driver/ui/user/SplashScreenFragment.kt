package h_mal.appttude.com.driver.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFragment
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

        userViewModel.getUser()

        goToLogin()
    }

    private fun goToLogin() = main{
            delay(1000)
            view?.navigateTo(R.id.to_loginFragment)
    }

}