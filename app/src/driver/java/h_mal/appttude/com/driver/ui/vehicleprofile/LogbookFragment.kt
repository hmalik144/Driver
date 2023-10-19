package h_mal.appttude.com.driver.ui.vehicleprofile

import android.net.Uri
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.ImageFormSubmissionFragment
import h_mal.appttude.com.driver.databinding.FragmentLogbookBinding
import h_mal.appttude.com.driver.model.Logbook
import h_mal.appttude.com.driver.utils.isTrue
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.LogbookViewModel


class LogbookFragment :
    ImageFormSubmissionFragment<LogbookViewModel, FragmentLogbookBinding, Logbook>() {

    override fun setupView(binding: FragmentLogbookBinding) = binding.run {
        v5cNo.setTextOnChange { model.v5cnumber = it }
        uploadLb.setOnClickListener { openGalleryForImageSelection() }
        submit.setOnClickListener { submit() }
    }

    override fun submit() {
        super.submit()
        applyBinding {
            validateEditTexts(v5cNo).isTrue {
                submitDocument()
            }
        }
    }

    override fun setFields(data: Logbook) {
        applyBinding {
            v5cNo.setText(data.v5cnumber)
        }
    }

    override fun setImage(image: StorageReference, thumbnail: StorageReference) {
        binding.logBookImg.setGlideImage(thumbnail)
    }

    override fun onImageGalleryResult(imageUri: Uri) {
        super.onImageGalleryResult(imageUri)
        binding.logBookImg.setGlideImage(imageUri)
    }
}