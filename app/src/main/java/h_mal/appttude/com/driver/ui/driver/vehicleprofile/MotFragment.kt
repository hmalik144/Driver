package h_mal.appttude.com.driver.ui.driver.vehicleprofile

import android.net.Uri
import android.os.Bundle
import android.view.View
import h_mal.appttude.com.driver.DataFieldsInterface
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.DataSubmissionBaseFragment
import h_mal.appttude.com.driver.model.MotObject
import h_mal.appttude.com.driver.utils.setPicassoImage
import h_mal.appttude.com.driver.viewmodels.MotViewModel
import kotlinx.android.synthetic.main.fragment_mot.*


class MotFragment: DataSubmissionBaseFragment<MotViewModel, MotObject>(),
    DataFieldsInterface {

    private val viewmodel by getFragmentViewModel<MotViewModel>()
    override fun getViewModel(): MotViewModel = viewmodel
    override var model = MotObject()
    override fun getLayoutId(): Int = R.layout.fragment_mot

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mot_expiry.setTextOnChange{ model.motExpiry = it }
        uploadmot.setOnClickListener { openGalleryWithPermissionRequest() }
        submit_mot.setOnClickListener { submit() }
    }

    override fun submit() {
        super.submit()
        validateEditTexts(mot_expiry)
            .takeIf { !it }
            ?.let { return }
        viewmodel.setDataInDatabase(model, picUri)
    }

    override fun setFields(data: MotObject) {
        super.setFields(data)
        mot_img.setPicassoImage(data.motImageString)
        mot_expiry.setFieldFromDataFetch(data.motExpiry)
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        mot_img.setImageURI(imageUri)
    }
}