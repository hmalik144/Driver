package h_mal.appttude.com.driver.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import h_mal.appttude.com.driver.base.ImageDocument


@IgnoreExtraProperties
data class DriversLicense(
    var licenseImageString: String? = null,
    var licenseNumber: String? = null,
    var licenseExpiry: String? = null
) : ImageDocument {
    @Exclude
    override fun getImageFileName(): String? {
        return licenseImageString
    }
    @Exclude
    override fun setImageFileName(fileName: String) {
        licenseImageString = fileName
    }
}