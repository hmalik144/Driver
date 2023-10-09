package h_mal.appttude.com.driver.ui.vehicleprofile

import android.net.Uri
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.DataSubmissionBaseFragment
import h_mal.appttude.com.driver.databinding.FragmentPrivateHireLicenseBinding
import h_mal.appttude.com.driver.dialogs.DateDialog
import h_mal.appttude.com.driver.model.PrivateHireVehicle
import h_mal.appttude.com.driver.utils.isTrue
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.PrivateHireVehicleViewModel


class PrivateHireVehicleFragment :
    DataSubmissionBaseFragment<PrivateHireVehicleViewModel, FragmentPrivateHireLicenseBinding, PrivateHireVehicle>() {

    override fun setupView(binding: FragmentPrivateHireLicenseBinding) = binding.run {
        phNo.setTextOnChange { model.phCarNumber = it }
        phExpiry.apply {
            setOnClickListener {
                DateDialog(this) { date ->
                    model.phCarExpiry = date
                }
            }
        }

        uploadphlic.setOnClickListener { openGalleryWithPermissionRequest() }
        submit.setOnClickListener {
            validateEditTexts(phNo, phExpiry).isTrue {
                viewModel.setDataInDatabase(model, picUri)
            }
        }
    }

    override fun setFields(data: PrivateHireVehicle) {
        super.setFields(data)
        applyBinding {
            phNo.setText(data.phCarNumber)
            phExpiry.setText(data.phCarExpiry)
            data.phCarImageString?.setImages { imageView2.setGlideImage(it.second) }
        }

    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        applyBinding {
            imageView2.setGlideImage(imageUri)
        }
    }
}