package h_mal.appttude.com.driver.ui.vehicleprofile

import h_mal.appttude.com.driver.base.DataViewerFragment
import h_mal.appttude.com.driver.databinding.FragmentMotBinding
import h_mal.appttude.com.driver.model.Mot
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.MotViewModel


class MotFragment : DataViewerFragment<MotViewModel, FragmentMotBinding, Mot>() {

    override fun setupView(binding: FragmentMotBinding) {
        super.setupView(binding)
        viewsToHide(binding.submit, binding.uploadmot)
    }

    override fun setFields(data: Mot) {
        super.setFields(data)
        applyBinding {
            motImg.setGlideImage(data.motImageString)
            motExpiry.setText(data.motExpiry)
        }
    }

}