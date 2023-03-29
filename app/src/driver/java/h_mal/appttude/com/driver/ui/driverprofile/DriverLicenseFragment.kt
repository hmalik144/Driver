package h_mal.appttude.com.driver.ui.driverprofile

import android.net.Uri
import h_mal.appttude.com.driver.base.DataSubmissionBaseFragment
import h_mal.appttude.com.driver.databinding.FragmentDriverLicenseBinding
import h_mal.appttude.com.driver.dialogs.DateDialog
import h_mal.appttude.com.driver.model.DriversLicense
import h_mal.appttude.com.driver.utils.isTrue
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.DriverLicenseViewModel

class DriverLicenseFragment :
    DataSubmissionBaseFragment<DriverLicenseViewModel, FragmentDriverLicenseBinding, DriversLicense>() {

    override var model = DriversLicense()

    override fun setupView(binding: FragmentDriverLicenseBinding) {
        binding.apply {
            licExpiry.apply {
                setTextOnChange { model.licenseExpiry = it }
                setOnClickListener {
                    DateDialog(this) { date ->
                        model.licenseExpiry = date
                    }
                }
            }
            licNo.setTextOnChange { model.licenseNumber = it }

            searchImage.setOnClickListener { openGalleryWithPermissionRequest() }
            submit.setOnClickListener {
                validateEditTexts(licExpiry, licNo).isTrue {
                    viewModel.setDataInDatabase(model, picUri)
                }
            }
        }
    }

    override fun setFields(data: DriversLicense) {
        super.setFields(data)
        applyBinding {
            driversliImg.setGlideImage(data.licenseImageString)
            licNo.setText(data.licenseNumber)
            licExpiry.setText(data.licenseExpiry)
        }
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        applyBinding {
            driversliImg.setGlideImage(imageUri)
        }
    }

}