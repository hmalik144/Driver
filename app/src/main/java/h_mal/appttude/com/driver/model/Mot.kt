package h_mal.appttude.com.driver.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import h_mal.appttude.com.driver.base.ImageDocument

@IgnoreExtraProperties
data class Mot(
    var motImageString: String? = null,
    var motExpiry: String? = null
) : ImageDocument {
    @Exclude
    override fun getImageFileName(): String? {
        return motImageString
    }

    @Exclude
    override fun setImageFileName(fileName: String) {
        motImageString = fileName
    }
}