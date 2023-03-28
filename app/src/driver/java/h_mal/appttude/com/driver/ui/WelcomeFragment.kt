package h_mal.appttude.com.driver.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.databinding.FragmentWelcomeBinding
import h_mal.appttude.com.driver.utils.navigateTo


class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentWelcomeBinding.inflate(inflater, container, false).apply {
            emailSignInButton.setOnClickListener {
                it.navigateTo(R.id.to_driverOverallFragment)
            }
        }.root
    }
}