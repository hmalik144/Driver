package h_mal.appttude.com.ui.user

import android.os.Bundle
import android.view.View
import h_mal.appttude.com.R
import h_mal.appttude.com.base.BaseFragment
import h_mal.appttude.com.data.FirebaseCompletion
import h_mal.appttude.com.databinding.SplashScreenBinding
import h_mal.appttude.com.utils.navigateTo
import h_mal.appttude.com.viewmodels.UserViewModel


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