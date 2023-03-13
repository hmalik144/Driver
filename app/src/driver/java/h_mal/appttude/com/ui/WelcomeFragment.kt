package h_mal.appttude.com.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import h_mal.appttude.com.R
import h_mal.appttude.com.utils.navigateTo
import kotlinx.android.synthetic.driver.fragment_welcome.*


class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email_sign_in_button.setOnClickListener {
            view.navigateTo(R.id.to_driverOverallFragment)
        }
    }
}