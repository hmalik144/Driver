package h_mal.appttude.com.driver.ui.driver.driverprofile

import android.net.Uri
import android.os.Bundle
import android.view.View
import h_mal.appttude.com.driver.DataFieldsInterface
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.DataSubmissionBaseFragment
import h_mal.appttude.com.driver.model.PrivateHireObject
import h_mal.appttude.com.driver.utils.setPicassoImage
import h_mal.appttude.com.driver.viewmodels.PrivateHireLicenseViewModel
import kotlinx.android.synthetic.main.fragment_private_hire_license.*


class PrivateHireLicenseFragment : DataSubmissionBaseFragment<PrivateHireLicenseViewModel, PrivateHireObject>(),
    DataFieldsInterface {

    val viewmodel by getFragmentViewModel<PrivateHireLicenseViewModel>()
    override fun getViewModel(): PrivateHireLicenseViewModel = viewmodel
    override var model =  PrivateHireObject()
    override fun getLayoutId(): Int = R.layout.fragment_private_hire_license

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ph_no.setTextOnChange{ model.phNumber = it }
        ph_expiry.setTextOnChange{ model.phExpiry = it }

        uploadphlic.setOnClickListener { openGalleryWithPermissionRequest() }
        submit.setOnClickListener{ submit() }
    }

    override fun submit(){
        validateEditTexts(ph_no,ph_expiry)
            .takeIf { !it }
            ?.let { return }

        viewmodel.setDataInDatabase(model, picUri)
    }

    override fun setFields(data: PrivateHireObject) {
        super.setFields(data)
        imageView2.setPicassoImage(data.phImageString)
        ph_no.setFieldFromDataFetch(data.phNumber)
        ph_expiry.setFieldFromDataFetch(data.phExpiry)
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        imageView2.setImageURI(imageUri)
    }

}