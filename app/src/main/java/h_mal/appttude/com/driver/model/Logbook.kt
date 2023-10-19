package h_mal.appttude.com.driver.model

import com.google.firebase.database.Exclude
import h_mal.appttude.com.driver.base.Document
import h_mal.appttude.com.driver.base.ImageDocument


data class Logbook(
    var photoString: String? = null,
    var v5cnumber: String? = null
) : ImageDocument{
    @Exclude
    override fun getImageFileName(): String? {
        return photoString
    }
    @Exclude
    override fun setImageFileName(fileName: String) {
        photoString = fileName
    }
}
