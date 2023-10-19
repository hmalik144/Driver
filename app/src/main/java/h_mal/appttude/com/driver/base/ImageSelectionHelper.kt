package h_mal.appttude.com.driver.base

import android.content.ClipData
import android.content.Intent
import android.net.Uri

interface ImageSelectionHelper {

    fun openGalleryForImageSelection()
    fun imageSelectorIntent(multiImage: Boolean = false) = Intent(Intent.ACTION_GET_CONTENT)
        .addCategory(Intent.CATEGORY_OPENABLE)
        .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, multiImage)
        .setType("image/*")

    fun Intent?.parseMultiImageIntent(): List<Uri> {
        this?.clipData?.takeIf { it.itemCount > 1 }?.convertToList()?.let { clip ->
            val list = clip.takeIf { it.size > 10 }?.let {
                clip.subList(0, 9)
            } ?: clip
            return list
        }
        return listOfNotNull(this?.data)
    }

    private fun ClipData.convertToList(): List<Uri> = 0.rangeTo(itemCount).map { getItemAt(it).uri }

}