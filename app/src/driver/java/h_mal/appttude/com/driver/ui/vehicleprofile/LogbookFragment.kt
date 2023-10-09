package h_mal.appttude.com.driver.ui.vehicleprofile

import android.net.Uri
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseFragment
import h_mal.appttude.com.driver.databinding.FragmentLogbookBinding
import h_mal.appttude.com.driver.model.Logbook
import h_mal.appttude.com.driver.utils.isTrue
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.LogbookViewModel


class LogbookFragment :
    DataSubmissionBaseFragment<LogbookViewModel, FragmentLogbookBinding, Logbook>() {

    override fun setupView(binding: FragmentLogbookBinding) = binding.run {
        v5cNo.setTextOnChange { model.v5cnumber = it }
        uploadLb.setOnClickListener { openGalleryWithPermissionRequest() }
        submit.setOnClickListener { submit() }
    }

    override fun submit() {
        super.submit()
        applyBinding {
            validateEditTexts(v5cNo).isTrue {
                viewModel.setDataInDatabase(model, picUri)
            }
        }
    }

    override fun setFields(data: Logbook) {
        super.setFields(data)
        applyBinding {
            v5cNo.setText(data.v5cnumber)
            data.photoString?.setImages { logBookImg.setGlideImage(it.second) }
        }

    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        applyBinding {
            logBookImg.setGlideImage(picUri)
        }
    }
}