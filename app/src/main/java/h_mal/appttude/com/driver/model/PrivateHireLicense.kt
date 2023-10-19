package h_mal.appttude.com.driver.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import h_mal.appttude.com.driver.base.ImageDocument


@IgnoreExtraProperties
data class PrivateHireLicense(
    var phImageString: String? = null,
    var phNumber: String? = null,
    var phExpiry: String? = null
) : ImageDocument {
    @Exclude
    override fun getImageFileName(): String? {
        return phImageString
    }

    @Exclude
    override fun setImageFileName(fileName: String) {
        phImageString = fileName
    }
}