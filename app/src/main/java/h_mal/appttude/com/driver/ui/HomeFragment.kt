package h_mal.appttude.com.driver.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.databinding.FragmentHomeBinding
import h_mal.appttude.com.driver.viewmodels.MainViewModel


class HomeFragment : BaseFragment<MainViewModel, FragmentHomeBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyBinding {
            enter.setOnClickListener { navigateToMain() }
        }
    }

    private fun navigateToMain() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }
}