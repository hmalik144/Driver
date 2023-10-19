package h_mal.appttude.com.driver.ui.driverprofile

import android.net.Uri
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.ImageFormSubmissionFragment
import h_mal.appttude.com.driver.databinding.FragmentDriverLicenseBinding
import h_mal.appttude.com.driver.dialogs.DateDialog
import h_mal.appttude.com.driver.model.DriversLicense
import h_mal.appttude.com.driver.utils.isTrue
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.DriverLicenseViewModel

class DriverLicenseFragment :
    ImageFormSubmissionFragment<DriverLicenseViewModel, FragmentDriverLicenseBinding, DriversLicense>() {

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

            searchImage.setOnClickListener { openGalleryForImageSelection() }
            submit.setOnClickListener {
                validateEditTexts(licExpiry, licNo).isTrue { submitDocument() }
            }
        }
    }

    override fun setFields(data: DriversLicense) {
        applyBinding {
            licNo.setText(data.licenseNumber)
            licExpiry.setText(data.licenseExpiry)
        }
    }

    override fun setImage(image: StorageReference?, thumbnail: StorageReference?) {
        thumbnail?.let {
            binding.driversliImg.setGlideImage(it)
            return
        }
        binding.driversliImg.setGlideImage(image)
    }

    override fun onImageGalleryResult(imageUri: Uri) {
        super.onImageGalleryResult(imageUri)
        binding.driversliImg.setGlideImage(imageUri)
    }

}