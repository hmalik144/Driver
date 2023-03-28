package h_mal.appttude.com.driver.admin.objects


class UserObject {
    var profileName: String? = null
    var profileEmail: String? = null
    var profilePicString: String? = null

    constructor()
    constructor(profileName: String?, profileEmail: String?, profilePicString: String?) {
        this.profileName = profileName
        this.profileEmail = profileEmail
        this.profilePicString = profilePicString
    }
}