package h_mal.appttude.com.ui.vehicleprofile

import android.net.Uri
import h_mal.appttude.com.base.DataSubmissionBaseFragment
import h_mal.appttude.com.databinding.FragmentLogbookBinding
import h_mal.appttude.com.model.Logbook
import h_mal.appttude.com.utils.isTrue
import h_mal.appttude.com.utils.setGlideImage
import h_mal.appttude.com.viewmodels.LogbookViewModel


class LogbookFragment :
    DataSubmissionBaseFragment<LogbookViewModel, FragmentLogbookBinding, Logbook>() {

    override var model = Logbook()

    override fun setupView(binding: FragmentLogbookBinding) = binding.run {
        v5cNo.setTextOnChange { model.v5cnumber = it }
        uploadLb.setOnClickListener { openGalleryWithPermissionRequest() }
        submitLb.setOnClickListener { submit() }
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
            logBookImg.setGlideImage(data.photoString)
            v5cNo.setText(data.v5cnumber)
        }

    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        applyBinding {
            logBookImg.setGlideImage(picUri)
        }
    }
}