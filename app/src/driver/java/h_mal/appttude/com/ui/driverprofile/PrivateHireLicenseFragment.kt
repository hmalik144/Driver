package h_mal.appttude.com.ui.driverprofile

import android.net.Uri
import android.os.Bundle
import android.view.View
import h_mal.appttude.com.R
import h_mal.appttude.com.base.DataSubmissionBaseFragment
import h_mal.appttude.com.dialogs.DateDialog
import h_mal.appttude.com.model.PrivateHireObject
import h_mal.appttude.com.utils.setGlideImage
import h_mal.appttude.com.viewmodels.PrivateHireLicenseViewModel
import kotlinx.android.synthetic.main.fragment_private_hire_license.*


class PrivateHireLicenseFragment : DataSubmissionBaseFragment
<PrivateHireLicenseViewModel, PrivateHireObject>(R.layout.fragment_private_hire_license) {

    val viewmodel by getFragmentViewModel<PrivateHireLicenseViewModel>()
    override fun getViewModel(): PrivateHireLicenseViewModel = viewmodel
    override var model =  PrivateHireObject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ph_no.setTextOnChange{ model.phNumber = it }
        ph_expiry.apply {
            setTextOnChange{ model.phExpiry = it }
            setOnClickListener {
                DateDialog(this){ date ->
                    model.phExpiry = date
                }
            }
        }

        uploadphlic.setOnClickListener { openGalleryWithPermissionRequest() }
        submit.setOnClickListener{ submit() }
    }

    override fun submit(){
        validateEditTexts(ph_no,ph_expiry).takeIf { !it }?.let { return }

        viewmodel.setDataInDatabase(model, picUri)
    }

    override fun setFields(data: PrivateHireObject) {
        super.setFields(data)
        imageView2.setGlideImage(data.phImageString)
        ph_no.setText(data.phNumber)
        ph_expiry.setText(data.phExpiry)
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        imageView2.setGlideImage(imageUri)
    }

}