package h_mal.appttude.com.driver.ui.driverprofile

import android.net.Uri
import com.google.firebase.storage.StorageReference
import h_mal.appttude.com.driver.base.ImageFormSubmissionFragment
import h_mal.appttude.com.driver.databinding.FragmentDriverProfileBinding
import h_mal.appttude.com.driver.dialogs.DateDialog
import h_mal.appttude.com.driver.model.DriverProfile
import h_mal.appttude.com.driver.utils.isTrue
import h_mal.appttude.com.driver.utils.setGlideImage
import h_mal.appttude.com.driver.viewmodels.DriverProfileViewModel


class DriverProfileFragment :
    ImageFormSubmissionFragment<DriverProfileViewModel, FragmentDriverProfileBinding, DriverProfile>() {

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
        addPhoto.setOnClickListener { openGalleryForImageSelection() }
        submit.setOnClickListener { submit() }
    }

    override fun submit() {
        applyBinding {
            validateEditTexts(
                namesInput, addressInput, postcodeInput,
                dobInput, niNumber, dateFirst
            ).isTrue {
                submitDocument()
            }
        }
    }

    override fun setFields(data: DriverProfile) {
        applyBinding {
            namesInput.setText(data.forenames)
            addressInput.setText(data.address)
            postcodeInput.setText(data.postcode)
            dobInput.setText(data.dob)
            niNumber.setText(data.ni)
            dateFirst.setText(data.dateFirst)
        }
    }

    override fun setImage(image: StorageReference, thumbnail: StorageReference) {
        binding.driverPic.setGlideImage(thumbnail)
    }

    override fun onImageGalleryResult(imageUri: Uri) {
        super.onImageGalleryResult(imageUri)
        binding.driverPic.setGlideImage(imageUri)
    }

}