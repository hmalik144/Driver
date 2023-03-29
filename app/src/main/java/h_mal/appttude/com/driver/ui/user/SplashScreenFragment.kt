package h_mal.appttude.com.driver.ui.user

import android.os.Bundle
import android.view.View
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.data.FirebaseCompletion
import h_mal.appttude.com.driver.databinding.SplashScreenBinding
import h_mal.appttude.com.driver.utils.navigateTo
import h_mal.appttude.com.driver.viewmodels.UserViewModel


class SplashScreenFragment : BaseFragment<UserViewModel, SplashScreenBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.splashscreenCheckUserIsLoggedIn()
    }

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        if (data is FirebaseCompletion.Default) view?.navigateTo(R.id.to_loginFragment)
    }

}