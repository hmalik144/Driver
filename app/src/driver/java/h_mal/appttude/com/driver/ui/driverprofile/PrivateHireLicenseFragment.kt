package h_mal.appttude.com.driver.ui.driverprofile

import android.net.Uri
import h_mal.appttude.com.driver.base.DataSubmissionBaseFragment
import h_mal.appttude.com.driver.databinding.FragmentPrivateHireLicenseBinding
import h_mal.appttude.com.driver.dialogs.DateDialog
import h_mal.appttude.com.driver.model.PrivateHireLicense
import h_mal.appttude.com.driver.utils.isTrue
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.PrivateHireLicenseViewModel


class PrivateHireLicenseFragment : DataSubmissionBaseFragment
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

        uploadphlic.setOnClickListener { openGalleryWithPermissionRequest() }
        submit.setOnClickListener { submit() }
    }

    override fun submit() {
        applyBinding {
            validateEditTexts(phNo, phExpiry).isTrue {
                viewModel.setDataInDatabase(model, picUri)
            }
        }
    }

    override fun setFields(data: PrivateHireLicense) {
        super.setFields(data)
        applyBinding {
            imageView2.setGlideImage(data.phImageString)
            phNo.setText(data.phNumber)
            phExpiry.setText(data.phExpiry)
        }
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        applyBinding {
            imageView2.setGlideImage(imageUri)
        }
    }

}