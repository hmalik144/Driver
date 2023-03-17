package h_mal.appttude.com.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import h_mal.appttude.com.R
import h_mal.appttude.com.utils.navigateTo
import kotlinx.android.synthetic.main.fragment_driver_overall.*

class DriverOverallFragment : Fragment(R.layout.fragment_driver_overall) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        driver_prof.setOnClickListener {
            it.navigateTo(R.id.to_driverProfileFragment)
        }
        private_hire.setOnClickListener {
            it.navigateTo(R.id.to_privateHireLicenseFragment2)
        }
        drivers_license.setOnClickListener {
            it.navigateTo(R.id.to_driverLicenseFragment)
        }
    }
}