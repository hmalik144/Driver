package h_mal.appttude.com.ui.vehicleprofile

import android.net.Uri
import android.os.Bundle
import android.view.View
import h_mal.appttude.com.model.LogbookObject
import h_mal.appttude.com.R
import h_mal.appttude.com.base.DataSubmissionBaseFragment
import h_mal.appttude.com.utils.setGlideImage
import h_mal.appttude.com.viewmodels.LogbookViewModel
import kotlinx.android.synthetic.main.fragment_logbook.*


class LogbookFragment : DataSubmissionBaseFragment<LogbookViewModel, LogbookObject>(R.layout.fragment_logbook) {

    private val viewmodel by getFragmentViewModel<LogbookViewModel>()
    override fun getViewModel(): LogbookViewModel = viewmodel
    override var model = LogbookObject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        v5c_no.setTextOnChange{ model.v5cnumber = it }
        upload_lb.setOnClickListener { openGalleryWithPermissionRequest() }
        submit_lb.setOnClickListener { submit() }
    }

    override fun submit() {
        super.submit()
        validateEditTexts(v5c_no)
            .takeIf { !it }
            ?.let { return }

        viewmodel.setDataInDatabase(model, picUri)
    }

    override fun setFields(data: LogbookObject) {
        super.setFields(data)

        log_book_img.setGlideImage(data.photoString)
        v5c_no.setText(data.v5cnumber)
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)

        picUri = imageUri
        log_book_img.setGlideImage(picUri)
    }
}