package h_mal.appttude.com.driver.ui.driverprofile

import android.net.Uri
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.ImageFormSubmissionFragment
import h_mal.appttude.com.driver.databinding.FragmentPrivateHireLicenseBinding
import h_mal.appttude.com.driver.dialogs.DateDialog
import h_mal.appttude.com.driver.model.PrivateHireLicense
import h_mal.appttude.com.driver.utils.isTrue
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.PrivateHireLicenseViewModel


class PrivateHireLicenseFragment : ImageFormSubmissionFragment
<PrivateHireLicenseViewModel, FragmentPrivateHireLicenseBinding, PrivateHireLicense>() {

    override fun setupView(binding: FragmentPrivateHireLicenseBinding) = binding.run {
        phNo.setTextOnChange { model.phNumber = it }
        phExpiry.apply {
            setTextOnChange { model.phExpiry = it }
            setOnClickListener {
                DateDialog(this) { date ->
                    model.phExpiry = date
                }
            }
        }

        uploadphlic.setOnClickListener { openGalleryForImageSelection() }
        submit.setOnClickListener { submit() }
    }

    override fun submit() {
        applyBinding {
            validateEditTexts(phNo, phExpiry).isTrue {
                submitDocument()
            }
        }
    }

    override fun setFields(data: PrivateHireLicense) {
        super.setFields(data)
        applyBinding {
            phNo.setText(data.phNumber)
            phExpiry.setText(data.phExpiry)
        }
    }

    override fun setImage(image: StorageReference, thumbnail: StorageReference) {
        binding.imageView2.setGlideImage(thumbnail)
    }

    override fun onImageGalleryResult(imageUri: Uri) {
        super.onImageGalleryResult(imageUri)
        binding.imageView2.setGlideImage(imageUri)
    }

}