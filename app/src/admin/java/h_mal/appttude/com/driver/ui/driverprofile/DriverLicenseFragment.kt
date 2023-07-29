package h_mal.appttude.com.driver.ui.driverprofile

import h_mal.appttude.com.driver.base.DataViewerFragment
import h_mal.appttude.com.driver.databinding.FragmentDriverLicenseBinding
import h_mal.appttude.com.driver.model.DriversLicense
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.DriverLicenseViewModel

class DriverLicenseFragment :
    DataViewerFragment<DriverLicenseViewModel, FragmentDriverLicenseBinding, DriversLicense>() {

    override fun setupView(binding: FragmentDriverLicenseBinding) {
        super.setupView(binding)
        viewsToHide(binding.submit, binding.searchImage)
    }

    override fun setFields(data: DriversLicense) {
        super.setFields(data)
        applyBinding {
            driversliImg.setGlideImage(data.licenseImageString)
            licNo.setText(data.licenseNumber)
            licExpiry.setText(data.licenseExpiry)
        }
    }

}