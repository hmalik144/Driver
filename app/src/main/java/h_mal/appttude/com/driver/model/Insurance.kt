package h_mal.appttude.com.driver.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import h_mal.appttude.com.driver.base.MultiImageDocument

@IgnoreExtraProperties
data class Insurance(
    var photoStrings: MutableList<String>? = null,
    var insurerName: String? = null,
    var expiryDate: String? = null
) : MultiImageDocument {
    @Exclude
    override fun getImageFileNames(): List<String>? {
        return photoStrings
    }

    @Exclude
    override fun setImageFileNames(images: List<String>) {
        // update existing array
        photoStrings?.run{
            clear()
            addAll(images)
            return
        }
        // set new array of images
        photoStrings = mutableListOf<String>().apply {
            addAll(images)
        }
    }
}