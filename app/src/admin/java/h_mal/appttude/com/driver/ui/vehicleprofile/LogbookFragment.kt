package h_mal.appttude.com.driver.ui.vehicleprofile

import h_mal.appttude.com.driver.base.DataViewerFragment
import h_mal.appttude.com.driver.databinding.FragmentLogbookBinding
import h_mal.appttude.com.driver.model.Logbook
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.LogbookViewModel


class LogbookFragment :
    DataViewerFragment<LogbookViewModel, FragmentLogbookBinding, Logbook>() {

    override fun setupView(binding: FragmentLogbookBinding) {
        super.setupView(binding)
        viewsToHide(binding.submitLb, binding.uploadLb)
    }

    override fun setFields(data: Logbook) {
        super.setFields(data)
        applyBinding {
            logBookImg.setGlideImage(data.photoString)
            v5cNo.setText(data.v5cnumber)
        }
    }

}