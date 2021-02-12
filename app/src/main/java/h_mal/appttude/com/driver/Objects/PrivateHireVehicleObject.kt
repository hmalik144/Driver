package h_mal.appttude.com.driver.Objects



class PrivateHireVehicleObject {
    var phCarImageString: String? = null
    var phCarNumber: String? = null
    var phCarExpiry: String? = null

    constructor(phCarImageString: String?, phCarNumber: String?, phCarExpiry: String?) {
        this.phCarImageString = phCarImageString
        this.phCarNumber = phCarNumber
        this.phCarExpiry = phCarExpiry
    }

    constructor()
}