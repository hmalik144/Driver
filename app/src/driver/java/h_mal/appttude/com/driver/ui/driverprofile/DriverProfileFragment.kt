package h_mal.appttude.com.driver.ui.driverprofile

import android.net.Uri
import h_mal.appttude.com.driver.databinding.FragmentDriverProfileBinding
import h_mal.appttude.com.driver.base.DataSubmissionBaseFragment
import h_mal.appttude.com.driver.dialogs.DateDialog
import h_mal.appttude.com.driver.model.DriverProfile
import h_mal.appttude.com.driver.utils.isTrue
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.DriverProfileViewModel


class DriverProfileFragment :
    DataSubmissionBaseFragment<DriverProfileViewModel, FragmentDriverProfileBinding, DriverProfile>() {

    override var model = DriverProfile()

    override fun setupView(binding: FragmentDriverProfileBinding) = binding.run {
        namesInput.setTextOnChange { model.forenames = it }
        addressInput.setTextOnChange { model.address = it }
        postcodeInput.setTextOnChange { model.postcode = it }
        dobInput.apply {
            setTextOnChange { model.dob = it }
            setOnClickListener {
                DateDialog(this) { date ->
                    model.dob = date
                }
            }
        }
        niNumber.setTextOnChange { model.ni = it }
        dateFirst.apply {
            setTextOnChange { model.dateFirst = it }
            setOnClickListener {
                DateDialog(this) { date ->
                    model.dateFirst = date
                }
            }
        }
        addPhoto.setOnClickListener { openGalleryWithPermissionRequest() }
        submitDriver.setOnClickListener { submit() }
    }

    override fun submit() {
        applyBinding {
            validateEditTexts(
                namesInput, addressInput, postcodeInput,
                dobInput, niNumber, dateFirst
            ).isTrue {
                viewModel.setDataInDatabase(model, picUri)
            }
        }
    }

    override fun setFields(data: DriverProfile) {
        super.setFields(data)
        applyBinding {
            driverPic.setGlideImage(data.driverPic)
            namesInput.setText(data.forenames)
            addressInput.setText(data.address)
            postcodeInput.setText(data.postcode)
            dobInput.setText(data.dob)
            niNumber.setText(data.ni)
            dateFirst.setText(data.dateFirst)
        }
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        applyBinding {
            driverPic.setGlideImage(imageUri)
        }
    }

}