package h_mal.appttude.com.driver.Objects



class DriverProfileObject {
    var driverPic: String? = null
    var forenames: String? = null
    var address: String? = null
    var postcode: String? = null
    var dob: String? = null
    var ni: String? = null
    var dateFirst: String? = null

    constructor()
    constructor(
        driverPic: String?, forenames: String?, address: String?,
        postcode: String?, dob: String?, ni: String?, dateFirst: String?
    ) {
        this.driverPic = driverPic
        this.forenames = forenames
        this.address = address
        this.postcode = postcode
        this.dob = dob
        this.ni = ni
        this.dateFirst = dateFirst
    }
}