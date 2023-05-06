package h_mal.appttude.com.driver.ui.vehicleprofile

import android.net.Uri
import android.widget.ImageView
import h_mal.appttude.com.driver.base.DataViewerFragment
import h_mal.appttude.com.driver.databinding.FragmentInsuranceBinding
import h_mal.appttude.com.driver.model.Insurance
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.InsuranceViewModel


class InsuranceFragment :
    DataViewerFragment<InsuranceViewModel, FragmentInsuranceBinding, Insurance>() {

    override fun setupView(binding: FragmentInsuranceBinding) {
        super.setupView(binding)
        viewsToHide(binding.submitIns, binding.uploadInsurance)
    }


    private fun updateImageCarousal(list: List<Any?>) {
        applyBinding {
            carouselView.setImageClickListener(null)
            carouselView.setImageListener { i: Int, imageView: ImageView ->
                when (list[i]) {
                    is Uri -> {
                        imageView.setGlideImage(list[i] as Uri)
                    }
                    is String -> imageView.setGlideImage(list[i] as String)
                }
            }
            carouselView.pageCount = list.size
        }
    }

    override fun setFields(data: Insurance) {
        super.setFields(data)
        applyBinding {
            insurer.setText(data.insurerName)
            insuranceExp.setText(data.expiryDate)
            data.photoStrings?.let { updateImageCarousal(it) }
        }
    }

}