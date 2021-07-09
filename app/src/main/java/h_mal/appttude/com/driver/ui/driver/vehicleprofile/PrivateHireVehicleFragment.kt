package h_mal.appttude.com.driver.ui.driver.vehicleprofile

import android.net.Uri
import android.os.Bundle
import android.view.View

import h_mal.appttude.com.driver.Global.DateDialog
import h_mal.appttude.com.driver.Objects.PrivateHireVehicleObject
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.DataSubmissionBaseFragment
import h_mal.appttude.com.driver.utils.setPicassoImage
import h_mal.appttude.com.driver.viewmodels.PrivateHireVehicleViewModel
import kotlinx.android.synthetic.main.fragment_private_hire_vehicle.*


class PrivateHireVehicleFragment: DataSubmissionBaseFragment<PrivateHireVehicleViewModel, PrivateHireVehicleObject>(){

    private val viewmodel by getFragmentViewModel<PrivateHireVehicleViewModel>()
    override fun getViewModel(): PrivateHireVehicleViewModel = viewmodel
    override var model = PrivateHireVehicleObject()
    override fun getLayoutId(): Int = R.layout.fragment_private_hire_vehicle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ph_no.setTextOnChange{ model.phCarNumber = it }
        ph_expiry.apply {
            setTextOnChange{ model.phCarExpiry = it }
            setOnClickListener {
                DateDialog(this)
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
        imageView2.setPicassoImage(data.phCarImageString)
        ph_no.setText(data.phCarNumber)
        ph_expiry.setText(data.phCarExpiry)
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        imageView2.setPicassoImage(imageUri)
    }
}