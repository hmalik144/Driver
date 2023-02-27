package h_mal.appttude.com.ui.vehicleprofile

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import h_mal.appttude.com.R
import h_mal.appttude.com.base.DataSubmissionBaseFragment
import h_mal.appttude.com.dialogs.DateDialog
import h_mal.appttude.com.model.InsuranceObject
import h_mal.appttude.com.utils.setGlideImage
import h_mal.appttude.com.viewmodels.InsuranceViewModel
import kotlinx.android.synthetic.main.fragment_insurance.*


class InsuranceFragment : DataSubmissionBaseFragment<InsuranceViewModel, InsuranceObject>(R.layout.fragment_insurance) {

    private var selectedImages: List<Uri>? = listOf()

    private val viewmodel: InsuranceViewModel by getFragmentViewModel()
    override fun getViewModel(): InsuranceViewModel = viewmodel
    override var model = InsuranceObject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImageSelectionAsMultiple()

        insurer.setTextOnChange { model.insurerName = it }
        insurance_exp.apply {
            setOnClickListener {
                DateDialog(this) { date ->
                    model.expiryDate = date
                }
            }
        }

        uploadInsurance.setOnClickListener { openGalleryWithPermissionRequest() }
        submit_ins.setOnClickListener { submit() }
    }

    private fun updateImageCarousal(list: List<Any?>) {
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