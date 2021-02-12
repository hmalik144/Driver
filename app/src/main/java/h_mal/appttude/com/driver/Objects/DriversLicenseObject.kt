package h_mal.appttude.com.driver.Objects



class DriversLicenseObject {
    var licenseImageString: String? = null
    var licenseNumber: String? = null
    var licenseExpiry: String? = null

    constructor()
    constructor(licenseImageString: String?, licenseNumber: String?, licenseExpiry: String?) {
        this.licenseImageString = licenseImageString
        this.licenseNumber = licenseNumber
        this.licenseExpiry = licenseExpiry
    }
}