package h_mal.appttude.com.data

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth

/**
 * Creates #LiveDate out of {UserAuthState} for firebase user state
 */
class FirebaseAuthStateLiveData(
    private val firebaseAuth: FirebaseAuth
) : LiveData<UserAuthState>() {

    override fun onActive() {
        super.onActive()
        firebaseAuth.addAuthStateListener(stateListener)
    }

    override fun onInactive() {
        super.onInactive()
        firebaseAuth.removeAuthStateListener(stateListener)
    }

    private val stateListener = FirebaseAuth.AuthStateListener { p0 ->
        if (p0.currentUser == null) {
            postValue(UserAuthState.LoggedOut)
        } else {
            postValue(UserAuthState.LoggedIn(p0.currentUser!!))
        }
    }
}