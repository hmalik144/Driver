package h_mal.appttude.com.driver.ui.driverprofile

import h_mal.appttude.com.driver.base.DataViewerFragment
import h_mal.appttude.com.driver.databinding.FragmentPrivateHireLicenseBinding
import h_mal.appttude.com.driver.model.PrivateHireLicense
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.PrivateHireLicenseViewModel


class PrivateHireLicenseFragment : DataViewerFragment<PrivateHireLicenseViewModel, FragmentPrivateHireLicenseBinding, PrivateHireLicense>() {

    override fun setupView(binding: FragmentPrivateHireLicenseBinding) {
        super.setupView(binding)
        viewsToHide(binding.submit, binding.uploadphlic)
    }

    override fun setFields(data: PrivateHireLicense) {
        super.setFields(data)
        applyBinding {
            imageView2.setGlideImage(data.phImageString)
            phNo.setText(data.phNumber)
            phExpiry.setText(data.phExpiry)
        }
    }

}