package h_mal.appttude.com.driver.ui.vehicleprofile

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseFragment
import h_mal.appttude.com.driver.databinding.FragmentInsuranceBinding
import h_mal.appttude.com.driver.dialogs.DateDialog
import h_mal.appttude.com.driver.model.Insurance
import h_mal.appttude.com.driver.utils.isTrue
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.InsuranceViewModel


class InsuranceFragment :
    DataSubmissionBaseFragment<InsuranceViewModel, FragmentInsuranceBinding, Insurance>() {

    private var selectedImages: List<Uri>? = listOf()

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
            submit.setOnClickListener { submit() }
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
                    is StorageReference -> imageView.setGlideImage(list[i] as StorageReference)
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

            data.photoStrings?.also {
                val keys = viewModel.getMultipleImagesAndThumbnails(it).map {i -> i.key }
                updateImageCarousal(keys)
            }
        }
    }

    override fun onImageGalleryResult(imageUris: List<Uri>?) {
        selectedImages = imageUris
        selectedImages?.let { updateImageCarousal(it) }
    }

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)

        if (data is Map<*,*>) {
            updateImageCarousal(data.map { it.value })
        }
    }

}