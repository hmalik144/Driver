package h_mal.appttude.com.ui.vehicleprofile

import android.net.Uri
import h_mal.appttude.com.base.DataSubmissionBaseFragment
import h_mal.appttude.com.databinding.FragmentMotBinding
import h_mal.appttude.com.dialogs.DateDialog
import h_mal.appttude.com.model.Mot
import h_mal.appttude.com.utils.isTrue
import h_mal.appttude.com.utils.setGlideImage
import h_mal.appttude.com.viewmodels.MotViewModel


class MotFragment : DataSubmissionBaseFragment<MotViewModel, FragmentMotBinding, Mot>() {

    override var model = Mot()

    override fun setupView(binding: FragmentMotBinding) = binding.run {
        motExpiry.apply {
            setOnClickListener {
                DateDialog(this) { date ->
                    model.motExpiry = date
                }
            }
        }

        uploadmot.setOnClickListener { openGalleryWithPermissionRequest() }
        submitMot.setOnClickListener {
            validateEditTexts(motExpiry).isTrue {
                viewModel.setDataInDatabase(model, picUri)
            }
        }
    }

    override fun setFields(data: Mot) {
        super.setFields(data)
        applyBinding {
            motImg.setGlideImage(data.motImageString)
            motExpiry.setText(data.motExpiry)
        }
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        applyBinding {
            motImg.setGlideImage(imageUri)
        }
    }
}