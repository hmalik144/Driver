package h_mal.appttude.com.driver.Objects



class PrivateHireObject {
    var phImageString: String? = null
    var phNumber: String? = null
    var phExpiry: String? = null

    constructor()
    constructor(phImageString: String?, phNumber: String?, phExpiry: String?) {
        this.phImageString = phImageString
        this.phNumber = phNumber
        this.phExpiry = phExpiry
    }
}