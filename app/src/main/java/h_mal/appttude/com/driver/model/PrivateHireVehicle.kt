package h_mal.appttude.com.driver.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import h_mal.appttude.com.driver.base.ImageDocument

@IgnoreExtraProperties
data class PrivateHireVehicle(
    var phCarImageString: String? = null,
    var phCarNumber: String? = null,
    var phCarExpiry: String? = null
) : ImageDocument {

    @Exclude
    override fun getImageFileName(): String? {
        return phCarImageString
    }

    @Exclude
    override fun setImageFileName(fileName: String) {
        phCarImageString = fileName
    }
}