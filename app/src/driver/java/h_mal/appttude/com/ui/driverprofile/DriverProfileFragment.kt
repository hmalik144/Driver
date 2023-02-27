package h_mal.appttude.com.ui.driverprofile

import android.net.Uri
import android.os.Bundle
import android.view.View
import h_mal.appttude.com.dialogs.DateDialog
import h_mal.appttude.com.R
import h_mal.appttude.com.base.DataSubmissionBaseFragment
import h_mal.appttude.com.model.DriverProfileObject
import h_mal.appttude.com.utils.setGlideImage
import h_mal.appttude.com.viewmodels.DriverProfileViewModel
import kotlinx.android.synthetic.main.fragment_driver_profile.*


class DriverProfileFragment: DataSubmissionBaseFragment
<DriverProfileViewModel, DriverProfileObject>(R.layout.fragment_driver_profile) {

    var localUri: Uri? = null

    private val viewmodel by getFragmentViewModel<DriverProfileViewModel>()
    override fun getViewModel(): DriverProfileViewModel = viewmodel
    override var model = DriverProfileObject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        names_input.setTextOnChange{ model.forenames = it }
        address_input.setTextOnChange{ model.address = it }
        postcode_input.setTextOnChange{ model.postcode = it }
        dob_input.apply {
                setTextOnChange{ model.dob = it }
                setOnClickListener {
                    DateDialog(this){ date ->
                        model.dob = date
                    }
                }
            }
        ni_number.setTextOnChange{ model.ni = it }
        date_first.apply {
            setTextOnChange{ model.dateFirst = it }
            setOnClickListener {
                DateDialog(this){ date ->
                    model.dateFirst = date
                }
            }
        }
        add_photo.setOnClickListener { openGalleryWithPermissionRequest() }
        submit_driver.setOnClickListener{ submit() }
    }

    override fun submit(){
        validateEditTexts(names_input, address_input, postcode_input,
            dob_input, ni_number, date_first)
            .takeIf { !it }
            ?.let { return }

        viewmodel.setDataInDatabase(model, localUri)
    }

    override fun setFields(data: DriverProfileObject) {
        super.setFields(data)
        driver_pic.setGlideImage(data.driverPic)
        names_input.setText(data.forenames)
        address_input.setText(data.address)
        postcode_input.setText(data.postcode)
        dob_input.setText(data.dob)
        ni_number.setText(data.ni)
        date_first.setText(data.dateFirst)
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        localUri = imageUri
        driver_pic.setGlideImage(imageUri)
    }

}