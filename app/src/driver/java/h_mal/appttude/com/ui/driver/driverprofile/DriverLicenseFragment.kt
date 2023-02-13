package h_mal.appttude.com.ui.driver.driverprofile

import android.net.Uri
import android.os.Bundle
import android.view.View
import h_mal.appttude.com.R
import h_mal.appttude.com.base.DataSubmissionBaseFragment
import h_mal.appttude.com.dialogs.DateDialog
import h_mal.appttude.com.model.DriversLicenseObject
import h_mal.appttude.com.utils.setGlideImage
import h_mal.appttude.com.viewmodels.DriverLicenseViewModel
import kotlinx.android.synthetic.main.fragment_driver_license.*

class DriverLicenseFragment :
    DataSubmissionBaseFragment<DriverLicenseViewModel, DriversLicenseObject>(R.layout.fragment_driver_license) {

    private val viewmodel: DriverLicenseViewModel by getFragmentViewModel()
    override fun getViewModel(): DriverLicenseViewModel = viewmodel
    override var model = DriversLicenseObject()

    private var imageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lic_expiry.apply {
            setTextOnChange { model.licenseExpiry = it }
            setOnClickListener {
                DateDialog(this) { date ->
                    model.licenseExpiry = date
                }
            }
        }
        lic_no.setTextOnChange { model.licenseNumber = it }

        search_image.setOnClickListener { openGalleryWithPermissionRequest() }
        submit.setOnClickListener { submit() }
    }

    override fun submit() {
        validateEditTexts(lic_expiry, lic_no).takeIf { !it }?.let { return }

        viewmodel.setDataInDatabase(model, imageUri)
    }

    override fun setFields(data: DriversLicenseObject) {
        super.setFields(data)
        driversli_img.setGlideImage(data.licenseImageString)
        lic_no.setText(data.licenseNumber)
        lic_expiry.setText(data.licenseExpiry)
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        this.imageUri = imageUri
        driversli_img.setGlideImage(imageUri)
    }

}