package h_mal.appttude.com.ui.vehicleprofile

import android.net.Uri
import android.os.Bundle
import android.view.View
import h_mal.appttude.com.R
import h_mal.appttude.com.base.DataSubmissionBaseFragment
import h_mal.appttude.com.dialogs.DateDialog
import h_mal.appttude.com.model.MotObject
import h_mal.appttude.com.utils.setGlideImage
import h_mal.appttude.com.viewmodels.MotViewModel
import kotlinx.android.synthetic.main.fragment_mot.*


class MotFragment: DataSubmissionBaseFragment<MotViewModel, MotObject>(R.layout.fragment_mot){

    private val viewmodel by getFragmentViewModel<MotViewModel>()
    override fun getViewModel(): MotViewModel = viewmodel
    override var model = MotObject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mot_expiry.apply {
            setOnClickListener {
                DateDialog(this){ date ->
                    model.motExpiry = date
                }
            }
        }

        uploadmot.setOnClickListener { openGalleryWithPermissionRequest() }
        submit_mot.setOnClickListener { submit() }
    }

    override fun submit() {
        super.submit()
        validateEditTexts(mot_expiry).takeIf { !it }?.let { return }
        viewmodel.setDataInDatabase(model, picUri)
    }

    override fun setFields(data: MotObject) {
        super.setFields(data)
        mot_img.setGlideImage(data.motImageString)
        mot_expiry.setText(data.motExpiry)
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)
        mot_img.setGlideImage(imageUri)
    }
}