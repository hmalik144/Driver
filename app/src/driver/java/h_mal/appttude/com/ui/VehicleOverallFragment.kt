package h_mal.appttude.com.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import h_mal.appttude.com.R
import h_mal.appttude.com.base.BaseFragment
import h_mal.appttude.com.utils.navigateTo
import h_mal.appttude.com.viewmodels.DriverLicenseViewModel
import kotlinx.android.synthetic.main.fragment_vehicle_overall.*


class VehicleOverallFragment : Fragment(R.layout.fragment_vehicle_overall) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vehicle_prof.setOnClickListener { it.navigateTo(R.id.to_vehicleSetupFragment) }
        insurance.setOnClickListener { it.navigateTo(R.id.to_insuranceFragment) }
        mot.setOnClickListener { it.navigateTo(R.id.to_motFragment) }
        logbook.setOnClickListener { it.navigateTo(R.id.to_logbookFragment) }
        private_hire_vehicle_license.setOnClickListener { it.navigateTo(R.id.to_privateHireVehicleFragment) }
    }
}