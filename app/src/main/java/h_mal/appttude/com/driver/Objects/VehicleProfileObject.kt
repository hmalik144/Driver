package h_mal.appttude.com.driver.Objects



class VehicleProfileObject {
    var reg: String? = null
    var make: String? = null
    var model: String? = null
    var colour: String? = null
    var keeperName: String? = null
    var keeperAddress: String? = null
    var keeperPostCode: String? = null
    var startDate: String? = null
    var isSeized: Boolean = false

    constructor()
    constructor(
        reg: String?, make: String?, model: String?, colour: String?, keeperName: String?,
        keeperAddress: String?, keeperPostCode: String?, startDate: String?, seized: Boolean
    ) {
        this.reg = reg
        this.make = make
        this.model = model
        this.colour = colour
        this.keeperName = keeperName
        this.keeperAddress = keeperAddress
        this.keeperPostCode = keeperPostCode
        this.startDate = startDate
        isSeized = seized
    }
}