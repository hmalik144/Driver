package h_mal.appttude.com.driver.data

sealed class FirebaseCompletion {
    object Default : FirebaseCompletion()
    data class Changed(val message: String) : FirebaseCompletion()
    data class ProfileDeleted(val message: String) : FirebaseCompletion()
}