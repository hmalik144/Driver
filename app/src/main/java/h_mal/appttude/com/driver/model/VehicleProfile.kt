package h_mal.appttude.com.driver.model


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
)