package h_mal.appttude.com.driver.ui.driver

import android.os.Bundle
import android.view.View
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.utils.navigateTo
import h_mal.appttude.com.driver.viewmodels.DriverLicenseViewModel
import kotlinx.android.synthetic.main.fragment_vehicle_overall.*


class VehicleOverallFragment : BaseFragment<DriverLicenseViewModel>() {

    private val viewmodel: DriverLicenseViewModel by getFragmentViewModel()
    override fun getViewModel(): DriverLicenseViewModel = viewmodel
    override fun getLayoutId(): Int = R.layout.fragment_vehicle_overall


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vehicle_prof.setOnClickListener { it.navigateTo(R.id.to_vehicleSetupFragment) }
        insurance.setOnClickListener { it.navigateTo(R.id.to_insuranceFragment) }
        mot.setOnClickListener { it.navigateTo(R.id.to_motFragment) }
        logbook.setOnClickListener { it.navigateTo(R.id.to_logbookFragment) }
        private_hire_vehicle_license.setOnClickListener { it.navigateTo(R.id.to_privateHireVehicleFragment) }
    }
}