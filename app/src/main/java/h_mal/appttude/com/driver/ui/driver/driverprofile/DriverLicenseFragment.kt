package h_mal.appttude.com.driver.ui.driver.driverprofile

import android.net.Uri
import android.os.Bundle
import android.view.View
import h_mal.appttude.com.driver.Global.DateDialog
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.DataSubmissionBaseFragment
import h_mal.appttude.com.driver.model.DriversLicenseObject
import h_mal.appttude.com.driver.utils.setPicassoImage
import h_mal.appttude.com.driver.viewmodels.DriverLicenseViewModel
import kotlinx.android.synthetic.main.fragment_driver_license.*

class DriverLicenseFragment : DataSubmissionBaseFragment<DriverLicenseViewModel, DriversLicenseObject>() {

    private val viewmodel: DriverLicenseViewModel by getFragmentViewModel()
    override fun getViewModel(): DriverLicenseViewModel = viewmodel
    override fun getLayoutId(): Int = R.layout.fragment_driver_license
    override var model = DriversLicenseObject()

    private var imageUri: Uri? = null
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lic_expiry.apply {
            setTextOnChange{ model.licenseExpiry = it }
            setOnClickListener {
                DateDialog(this)
            }
        }
        lic_no.setTextOnChange{ model.licenseNumber = it }

        upload_lic.setOnClickListener { openGalleryWithPermissionRequest() }
        submit.setOnClickListener{ submit() }
    }

    override fun submit(){
        validateEditTexts(lic_expiry,lic_no).takeIf { !it }?.let { return }

        viewmodel.setDataInDatabase(model, imageUri)
    }

    override fun setFields(data: DriversLicenseObject) {
        super.setFields(data)
        driversli_img.setPicassoImage(data.licenseImageString)
        lic_no.setText(data.licenseNumber)
        lic_expiry.setText(data.licenseExpiry)
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        this.imageUri = imageUri
        driversli_img.setPicassoImage(imageUri)
    }

}