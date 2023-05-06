package h_mal.appttude.com.driver.ui

import android.view.View
import android.widget.ListView
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.data.USER_CONST
import h_mal.appttude.com.driver.databinding.FragmentUserMainBinding
import h_mal.appttude.com.driver.utils.navigateTo
import h_mal.appttude.com.driver.utils.toBundle
import h_mal.appttude.com.driver.viewmodels.DriverOverviewViewModel
import java.io.IOException


class DriverOverviewFragment : BaseFragment<DriverOverviewViewModel, FragmentUserMainBinding>() {

    private lateinit var listView: ListView
    private lateinit var driverId: String

    override fun setupView(binding: FragmentUserMainBinding) {
        listView = binding.approvalsList

        driverId = requireArguments().getString(USER_CONST) ?: throw IOException("No user ID has been passed")
        viewModel.loadDriverApprovals(driverId)
    }

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        @Suppress("UNCHECKED_CAST")
        if (data is Map<*, *>) {
            if (listView.adapter == null) {
                listView.adapter = ApprovalListAdapter(layoutInflater, data as Map<String, Int?>) {
                    this.view?.applyNavigation(it)
                }
                listView.isScrollContainer = false
            } else {
                (listView.adapter as ApprovalListAdapter).updateAdapter(data as Map<String, Int?>)
            }
        }
    }

    private fun View.applyNavigation(key: String) {
        val navId = when (key) {
            context.getString(R.string.driver_profile) -> R.id.to_driverProfileFragment
            context.getString(R.string.drivers_license) -> R.id.to_driverLicenseFragment
            context.getString(R.string.private_hire_license) -> R.id.to_privateHireLicenseFragment
            context.getString(R.string.vehicle_profile) -> R.id.to_vehicleProfileFragment
            context.getString(R.string.insurance) -> R.id.to_insuranceFragment
            context.getString(R.string.m_o_t) -> R.id.to_motFragment
            context.getString(R.string.log_book) -> R.id.to_logbookFragment
            context.getString(R.string.private_hire_vehicle_license) -> R.id.to_privateHireVehicleFragment
            else -> {
                throw StringIndexOutOfBoundsException("No resource for $key")
            }
        }
        navigateTo(navId, driverId.toBundle(USER_CONST))
    }
}