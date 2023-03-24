package h_mal.appttude.com.ui.vehicleprofile

import android.net.Uri
import h_mal.appttude.com.base.DataSubmissionBaseFragment
import h_mal.appttude.com.databinding.FragmentPrivateHireLicenseBinding
import h_mal.appttude.com.dialogs.DateDialog
import h_mal.appttude.com.model.PrivateHireVehicle
import h_mal.appttude.com.utils.isTrue
import h_mal.appttude.com.utils.setGlideImage
import h_mal.appttude.com.viewmodels.PrivateHireVehicleViewModel


class PrivateHireVehicleFragment :
    DataSubmissionBaseFragment<PrivateHireVehicleViewModel, FragmentPrivateHireLicenseBinding, PrivateHireVehicle>() {

    override var model = PrivateHireVehicle()

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
            imageView2.setGlideImage(data.phCarImageString)
            phNo.setText(data.phCarNumber)
            phExpiry.setText(data.phCarExpiry)
        }

    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        applyBinding {
            imageView2.setGlideImage(imageUri)
        }
    }
}