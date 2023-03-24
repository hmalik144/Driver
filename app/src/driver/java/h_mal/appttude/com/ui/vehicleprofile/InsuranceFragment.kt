package h_mal.appttude.com.ui.vehicleprofile

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import h_mal.appttude.com.base.DataSubmissionBaseFragment
import h_mal.appttude.com.databinding.FragmentInsuranceBinding
import h_mal.appttude.com.dialogs.DateDialog
import h_mal.appttude.com.model.Insurance
import h_mal.appttude.com.utils.isTrue
import h_mal.appttude.com.utils.setGlideImage
import h_mal.appttude.com.viewmodels.InsuranceViewModel


class InsuranceFragment :
    DataSubmissionBaseFragment<InsuranceViewModel, FragmentInsuranceBinding, Insurance>() {

    private var selectedImages: List<Uri>? = listOf()

    override var model = Insurance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImageSelectionAsMultiple()

        applyBinding {
            insurer.setTextOnChange { model.insurerName = it }
            insuranceExp.apply {
                setOnClickListener {
                    DateDialog(this) { date ->
                        model.expiryDate = date
                    }
                }
            }
            uploadInsurance.setOnClickListener { openGalleryWithPermissionRequest() }
            submitIns.setOnClickListener { submit() }
        }
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

    override fun submit() {
        super.submit()
        applyBinding {
            validateEditTexts(insurer, insuranceExp).isTrue {
                viewModel.setDataInDatabase(model, selectedImages)
            }
        }
    }

    override fun setFields(data: Insurance) {
        super.setFields(data)
        applyBinding {
            insurer.setText(model.insurerName)
            insuranceExp.setText(model.expiryDate)
            model.photoStrings?.let { updateImageCarousal(it) }
        }
    }

    override fun onImageGalleryResult(imageUris: List<Uri>?) {
        selectedImages = imageUris
        selectedImages?.let { updateImageCarousal(it) }
    }

}