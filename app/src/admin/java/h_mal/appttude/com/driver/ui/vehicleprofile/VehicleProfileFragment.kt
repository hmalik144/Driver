package h_mal.appttude.com.driver.ui.vehicleprofile

import h_mal.appttude.com.driver.base.DataViewerFragment
import h_mal.appttude.com.driver.databinding.FragmentVehicleSetupBinding
import h_mal.appttude.com.driver.model.VehicleProfile
import h_mal.appttude.com.driver.viewmodels.VehicleProfileViewModel


class VehicleProfileFragment :
    DataViewerFragment<VehicleProfileViewModel, FragmentVehicleSetupBinding, VehicleProfile>() {

    override fun setupView(binding: FragmentVehicleSetupBinding) {
        super.setupView(binding)
        viewsToHide(binding.submit)
    }

    override fun setFields(data: VehicleProfile) {
        super.setFields(data)
        applyBinding {
            reg.setText(data.reg)
            make.setText(data.make)
            carModel.setText(data.model)
            colour.setText(data.colour)
            keeperName.setText(data.keeperName)
            address.setText(data.keeperAddress)
            postcode.setText(data.keeperPostCode)
            startDate.setText(data.startDate)
            seizedCheckbox.isChecked = data.isSeized
        }
    }
}