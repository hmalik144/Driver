package h_mal.appttude.com.driver.ui.vehicleprofile

import android.net.Uri
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseFragment
import h_mal.appttude.com.driver.databinding.FragmentMotBinding
import h_mal.appttude.com.driver.dialogs.DateDialog
import h_mal.appttude.com.driver.model.Mot
import h_mal.appttude.com.driver.utils.isTrue
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.MotViewModel


class MotFragment : DataSubmissionBaseFragment<MotViewModel, FragmentMotBinding, Mot>() {

    override fun setupView(binding: FragmentMotBinding) = binding.run {
        motExpiry.apply {
            setOnClickListener {
                DateDialog(this) { date ->
                    model.motExpiry = date
                }
            }
        }

        uploadmot.setOnClickListener { openGalleryWithPermissionRequest() }
        submit.setOnClickListener {
            validateEditTexts(motExpiry).isTrue {
                viewModel.setDataInDatabase(model, picUri)
            }
        }
    }

    override fun setFields(data: Mot) {
        super.setFields(data)
        applyBinding {
            motExpiry.setText(data.motExpiry)
            data.motImageString?.setImages { motImg.setGlideImage(it.second) }
        }
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        applyBinding {
            motImg.setGlideImage(imageUri)
        }
    }
}