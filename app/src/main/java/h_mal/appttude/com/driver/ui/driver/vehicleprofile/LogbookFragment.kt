package h_mal.appttude.com.driver.ui.driver.vehicleprofile

import android.net.Uri
import android.os.Bundle
import android.view.View
import h_mal.appttude.com.driver.Objects.LogbookObject
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.DataSubmissionBaseFragment
import h_mal.appttude.com.driver.utils.setPicassoImage
import h_mal.appttude.com.driver.viewmodels.LogbookViewModel
import kotlinx.android.synthetic.main.fragment_logbook.*


class LogbookFragment : DataSubmissionBaseFragment<LogbookViewModel, LogbookObject>() {

    private val viewmodel by getFragmentViewModel<LogbookViewModel>()
    override fun getViewModel(): LogbookViewModel = viewmodel
    override var model = LogbookObject()
    override fun getLayoutId(): Int = R.layout.fragment_logbook

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

        log_book_img.setPicassoImage(data.photoString)
        v5c_no.setText(data.v5cnumber)
    }

    override fun onImageGalleryResult(imageUri: Uri?) {
        super.onImageGalleryResult(imageUri)

        picUri = imageUri
        log_book_img.setPicassoImage(picUri)
    }
}