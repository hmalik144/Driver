package h_mal.appttude.com.driver.ui.driverprofile

import h_mal.appttude.com.driver.base.DataViewerFragment
import h_mal.appttude.com.driver.databinding.FragmentDriverProfileBinding
import h_mal.appttude.com.driver.model.DriverProfile
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.DriverProfileViewModel


class DriverProfileFragment :
    DataViewerFragment<DriverProfileViewModel, FragmentDriverProfileBinding, DriverProfile>() {

    override fun setupView(binding: FragmentDriverProfileBinding) {
        super.setupView(binding)
        viewsToHide(binding.submitDriver, binding.addPhoto)
    }

    override fun setFields(data: DriverProfile) {
        super.setFields(data)
        applyBinding {
            driverPic.setGlideImage(data.driverPic)
            namesInput.setText(data.forenames)
            addressInput.setText(data.address)
            postcodeInput.setText(data.postcode)
            dobInput.setText(data.dob)
            niNumber.setText(data.ni)
            dateFirst.setText(data.dateFirst)
        }
    }

}