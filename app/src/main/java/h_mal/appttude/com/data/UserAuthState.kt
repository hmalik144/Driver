package h_mal.appttude.com.data

import com.google.firebase.auth.FirebaseUser
import h_mal.appttude.com.utils.Event

sealed class UserAuthState {
    object LoggedOut : UserAuthState()
    class LoggedIn(val data: FirebaseUser) : UserAuthState()
}