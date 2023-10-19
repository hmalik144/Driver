package h_mal.appttude.com.driver.ui.vehicleprofile

import android.net.Uri
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.ImageFormSubmissionFragment
import h_mal.appttude.com.driver.databinding.FragmentMotBinding
import h_mal.appttude.com.driver.dialogs.DateDialog
import h_mal.appttude.com.driver.model.Mot
import h_mal.appttude.com.driver.utils.isTrue
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.MotViewModel


class MotFragment : ImageFormSubmissionFragment<MotViewModel, FragmentMotBinding, Mot>() {

    override fun setupView(binding: FragmentMotBinding) = binding.run {
        motExpiry.apply {
            setOnClickListener {
                DateDialog(this) { date ->
                    model.motExpiry = date
                }
            }
        }

        uploadmot.setOnClickListener { openGalleryForImageSelection() }
        submit.setOnClickListener {
            validateEditTexts(motExpiry).isTrue {
                submitDocument()
            }
        }
    }

    override fun setFields(data: Mot) {
        applyBinding {
            motExpiry.setText(data.motExpiry)
        }
    }

    override fun setImage(image: StorageReference, thumbnail: StorageReference) {
        binding.motImg.setGlideImage(thumbnail)
    }

    override fun onImageGalleryResult(imageUri: Uri) {
        super.onImageGalleryResult(imageUri)
        binding.motImg.setGlideImage(imageUri)
    }
}