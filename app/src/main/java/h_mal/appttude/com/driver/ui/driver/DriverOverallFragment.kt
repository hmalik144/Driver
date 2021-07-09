package h_mal.appttude.com.driver.ui.driver

import android.os.Bundle
import android.view.View
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.utils.navigateTo
import h_mal.appttude.com.driver.viewmodels.DriverLicenseViewModel

import kotlinx.android.synthetic.main.fragment_driver_overall.*

class DriverOverallFragment : BaseFragment<DriverLicenseViewModel>() {

    private val viewmodel: DriverLicenseViewModel by getFragmentViewModel()
    override fun getViewModel(): DriverLicenseViewModel = viewmodel
    override fun getLayoutId(): Int = R.layout.fragment_driver_overall

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