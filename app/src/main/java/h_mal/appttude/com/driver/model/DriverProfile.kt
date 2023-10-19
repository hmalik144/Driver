package h_mal.appttude.com.driver.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import h_mal.appttude.com.driver.base.Document
import h_mal.appttude.com.driver.base.ImageDocument

@IgnoreExtraProperties
data class DriverProfile(
    var driverPic: String? = null,
    var forenames: String? = null,
    var address: String? = null,
    var postcode: String? = null,
    var dob: String? = null,
    var ni: String? = null,
    var dateFirst: String? = null
) : ImageDocument {
    @Exclude
    override fun getImageFileName(): String? {
        return driverPic
    }

    @Exclude
    override fun setImageFileName(fileName: String) {
        driverPic = fileName
    }
}