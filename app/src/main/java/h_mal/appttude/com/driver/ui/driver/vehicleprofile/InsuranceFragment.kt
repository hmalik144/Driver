package h_mal.appttude.com.driver.ui.driver.vehicleprofile

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import h_mal.appttude.com.driver.Global.DateDialog
import h_mal.appttude.com.driver.Objects.InsuranceObject
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.DataSubmissionBaseFragment
import h_mal.appttude.com.driver.utils.setPicassoImage
import h_mal.appttude.com.driver.viewmodels.InsuranceViewModel
import kotlinx.android.synthetic.main.fragment_insurance.*


class InsuranceFragment : DataSubmissionBaseFragment<InsuranceViewModel, InsuranceObject>() {

    private var selectedImages: List<Uri>? = listOf()

    private val viewmodel: InsuranceViewModel by getFragmentViewModel()
    override fun getViewModel(): InsuranceViewModel = viewmodel
    override fun getLayoutId(): Int = R.layout.fragment_insurance
    override var model = InsuranceObject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImageSelectionAsMultiple()

        insurer.setTextOnChange { model.insurerName = it }
        insurance_exp.apply {
            setOnClickListener { DateDialog(this) }
            setTextOnChange { model.expiryDate = it }
        }

        uploadInsurance.setOnClickListener { openGalleryWithPermissionRequest() }
        submit_ins.setOnClickListener { submit() }
    }

    private fun updateImageCarousal(list: List<Any?>) {
        carouselView.setImageClickListener(null)
        carouselView.setImageListener { i: Int, imageView: ImageView ->
            when (list[i]) {
                is Uri -> { imageView.setPicassoImage(list[i] as Uri) }
                is String -> imageView.setPicassoImage(list[i] as String)
            }
        }
        carouselView.pageCount = list.size

    }

    override fun submit() {
        super.submit()
        validateEditTexts(insurer, insurance_exp).takeIf { !it }?.let { return }
        viewmodel.setDataInDatabase(model, selectedImages)
    }

    override fun setFields(data: InsuranceObject) {
        super.setFields(data)

        insurer.setText(model.insurerName)
        insurance_exp.setText(model.expiryDate)
        model.photoStrings?.let { updateImageCarousal(it) }
    }

    override fun onImageGalleryResult(imageUris: List<Uri>?) {
        selectedImages = imageUris
        selectedImages?.let { updateImageCarousal(it) }
    }

}