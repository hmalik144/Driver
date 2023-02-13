package h_mal.appttude.com.ui.driver.vehicleprofile

import android.net.Uri
import android.os.Bundle
import android.view.View

import h_mal.appttude.com.dialogs.DateDialog
import h_mal.appttude.com.model.PrivateHireVehicleObject
import h_mal.appttude.com.R
import h_mal.appttude.com.base.DataSubmissionBaseFragment
import h_mal.appttude.com.utils.setGlideImage
import h_mal.appttude.com.viewmodels.PrivateHireVehicleViewModel
import kotlinx.android.synthetic.main.fragment_private_hire_vehicle.*


class PrivateHireVehicleFragment: DataSubmissionBaseFragment
<PrivateHireVehicleViewModel, PrivateHireVehicleObject>(R.layout.fragment_private_hire_vehicle){

    private val viewmodel by getFragmentViewModel<PrivateHireVehicleViewModel>()
    override fun getViewModel(): PrivateHireVehicleViewModel = viewmodel
    override var model = PrivateHireVehicleObject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ph_no.setTextOnChange{ model.phCarNumber = it }
        ph_expiry.apply {
            setOnClickListener {
                DateDialog(this){ date ->
                    model.phCarExpiry = date
                }
            }
        }

        uploadphlic.setOnClickListener { openGalleryWithPermissionRequest() }
        submit.setOnClickListener { submit() }
    }

    override fun submit() {
        super.submit()
        validateEditTexts(ph_no, ph_expiry).takeIf { !it }?.let { return }
        viewmodel.setDataInDatabase(model, picUri)
    }

    override fun setFields(data: PrivateHireVehicleObject) {
        super.setFields(data)
        imageView2.setGlideImage(data.phCarImageString)
        ph_no.setText(data.phCarNumber)
        ph_expiry.setText(data.phCarExpiry)
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        imageView2.setGlideImage(imageUri)
    }
}