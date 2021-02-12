package h_mal.appttude.com.driver.Objects



class MotObject {
    var motImageString: String? = null
    var motExpiry: String? = null

    constructor()
    constructor(motImageString: String?, motExpiry: String?) {
        this.motImageString = motImageString
        this.motExpiry = motExpiry
    }
}