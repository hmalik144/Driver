package h_mal.appttude.com.driver.driver

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import h_mal.appttude.com.driver.DataFieldsInterface
import h_mal.appttude.com.driver.Objects.InsuranceObject
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.DataSubmissionBaseFragment
import h_mal.appttude.com.driver.utils.setPicassoImage
import h_mal.appttude.com.driver.viewmodels.InsuranceViewModel
import io.github.vejei.carouselview.CarouselAdapter
import kotlinx.android.synthetic.main.fragment_insurance.*


class InsuranceFragment : DataSubmissionBaseFragment<InsuranceViewModel, InsuranceObject>(),
    DataFieldsInterface {

    private var selectedImages: List<Uri>? = listOf()

    lateinit var adapter: PageAdapter

    private val viewmodel: InsuranceViewModel by getFragmentViewModel()
    override fun getViewModel(): InsuranceViewModel = viewmodel
    override fun getLayoutId(): Int = R.layout.fragment_insurance
    override var model = InsuranceObject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImageSelectionAsMultiple()

        adapter = PageAdapter()
        carousel_view.adapter = adapter

        insurer.setTextOnChange { model.insurerName = it }
        insurance_exp.setTextOnChange { model.expiryDate = it }

        uploadInsurance.setOnClickListener { openGalleryWithPermissionRequest() }
        submit_ins.setOnClickListener { submit() }
    }

    override fun submit() {
        super.submit()
        validateEditTexts(insurer, insurance_exp)
            .takeIf { !it }
            ?.let { return }
        viewmodel.setDataInDatabase(model, selectedImages)
    }

    override fun setFields(data: InsuranceObject) {
        super.setFields(data)

        insurer.setFieldFromDataFetch(model.insurerName)
        insurance_exp.setFieldFromDataFetch(model.expiryDate)
        model.photoStrings?.let { adapter.setData(it) }
    }

    override fun onImageGalleryResult(imageUris: List<Uri>?) {
        selectedImages = imageUris
        selectedImages?.let { adapter.setData(it) }
    }

    class PageAdapter : CarouselAdapter<PageAdapter.ViewHolder>() {
        private var data: List<Any?>? = null

        fun setData(list: List<Any?>) {
            data = list
            notifyDataSetChanged()
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val backgroundImageView = itemView.findViewById<ImageView>(R.id.imageView)

            fun bind(page: Any?) {
                backgroundImageView.clipToOutline = true
                when (page) {
                    is Uri -> backgroundImageView.setImageURI(page)
                    is String -> backgroundImageView.setPicassoImage(page)
                }
            }
        }

        override fun onCreatePageViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.carousal_image_cell, parent, false)
            )
        }

        override fun onBindPageViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(data?.get(position))
        }

        override fun getPageCount(): Int {
            return data?.size ?: 0
        }
    }

}