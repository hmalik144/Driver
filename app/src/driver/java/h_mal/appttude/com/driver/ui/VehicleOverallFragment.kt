package h_mal.appttude.com.driver.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.databinding.FragmentVehicleOverallBinding
import h_mal.appttude.com.driver.utils.navigateTo


class VehicleOverallFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentVehicleOverallBinding.inflate(inflater, container, false).apply {
            vehicleProf.setOnClickListener {
                it.navigateTo(R.id.to_vehicleSetupFragment)
            }
            insurance.setOnClickListener { it.navigateTo(R.id.to_insuranceFragment) }
            mot.setOnClickListener { it.navigateTo(R.id.to_motFragment) }
            logbook.setOnClickListener { it.navigateTo(R.id.to_logbookFragment) }
            privateHireVehicleLicense.setOnClickListener { it.navigateTo(R.id.to_privateHireVehicleFragment) }
        }.root
    }
}