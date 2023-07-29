package h_mal.appttude.com.driver.ui.vehicleprofile

import h_mal.appttude.com.driver.base.DataViewerFragment
import h_mal.appttude.com.driver.databinding.FragmentPrivateHireLicenseBinding
import h_mal.appttude.com.driver.model.PrivateHireVehicle
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.PrivateHireVehicleViewModel


class PrivateHireVehicleFragment :
    DataViewerFragment<PrivateHireVehicleViewModel, FragmentPrivateHireLicenseBinding, PrivateHireVehicle>() {

    override fun setupView(binding: FragmentPrivateHireLicenseBinding) {
        super.setupView(binding)
        viewsToHide(binding.submit, binding.uploadphlic)
    }

    override fun setFields(data: PrivateHireVehicle) {
        super.setFields(data)
        applyBinding {
            imageView2.setGlideImage(data.phCarImageString)
            phNo.setText(data.phCarNumber)
            phExpiry.setText(data.phCarExpiry)
        }

    }

}