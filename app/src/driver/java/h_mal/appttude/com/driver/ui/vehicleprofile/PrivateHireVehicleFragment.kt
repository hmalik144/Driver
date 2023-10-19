package h_mal.appttude.com.driver.ui.vehicleprofile

import android.net.Uri
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.ImageFormSubmissionFragment
import h_mal.appttude.com.driver.databinding.FragmentPrivateHireLicenseBinding
import h_mal.appttude.com.driver.dialogs.DateDialog
import h_mal.appttude.com.driver.model.PrivateHireVehicle
import h_mal.appttude.com.driver.utils.isTrue
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.PrivateHireVehicleViewModel


class PrivateHireVehicleFragment :
    ImageFormSubmissionFragment<PrivateHireVehicleViewModel, FragmentPrivateHireLicenseBinding, PrivateHireVehicle>() {

    override fun setupView(binding: FragmentPrivateHireLicenseBinding) = binding.run {
        phNo.setTextOnChange { model.phCarNumber = it }
        phExpiry.apply {
            setOnClickListener {
                DateDialog(this) { date ->
                    model.phCarExpiry = date
                }
            }
        }

        uploadphlic.setOnClickListener { openGalleryForImageSelection() }
        submit.setOnClickListener {
            validateEditTexts(phNo, phExpiry).isTrue {
                submitDocument()
            }
        }
    }

    override fun setFields(
        data: PrivateHireVehicle
    ) {
        applyBinding {
            phNo.setText(data.phCarNumber)
            phExpiry.setText(data.phCarExpiry)
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