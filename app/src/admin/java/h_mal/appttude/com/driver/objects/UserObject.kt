package h_mal.appttude.com.driver.objects

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserObject (
    var profileName: String? = "",
    var profileEmail: String? = "",
    var profilePicString: String? = "",
)