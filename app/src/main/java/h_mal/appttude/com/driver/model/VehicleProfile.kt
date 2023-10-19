package h_mal.appttude.com.driver.model

import com.google.firebase.database.IgnoreExtraProperties
import h_mal.appttude.com.driver.base.Document

@IgnoreExtraProperties
data class VehicleProfile(
    var reg: String? = null,
    var make: String? = null,
    var model: String? = null,
    var colour: String? = null,
    var keeperName: String? = null,
    var keeperAddress: String? = null,
    var keeperPostCode: String? = null,
    var startDate: String? = null,
    var isSeized: Boolean = false
) : Document