package h_mal.appttude.com.data

sealed class FirebaseCompletion {
    object Default : FirebaseCompletion()
    data class Changed(val message: String) : FirebaseCompletion()
    data class ProfileDeleted(val message: String) : FirebaseCompletion()
}