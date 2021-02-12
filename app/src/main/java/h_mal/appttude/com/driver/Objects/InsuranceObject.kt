package h_mal.appttude.com.driver.Objects



class InsuranceObject {
    var photoStrings: MutableList<String?>? = null
    var insurerName: String? = null
    var expiryDate: String? = null

    constructor()
    constructor(photoStrings: MutableList<String?>?, insurerName: String?, expiryDate: String?) {
        this.photoStrings = photoStrings
        this.insurerName = insurerName
        this.expiryDate = expiryDate
    }
}