package h_mal.appttude.com.driver.data

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

/**
 * Creates #LiveDate out of {UserAuthState} for firebase user state
 */
class FirebaseDataLiveData<T : Any>(
    private val reference: DatabaseReference,
    private val cls: Class<T>
) : LiveData<DataState>() {

    override fun onActive() {
        super.onActive()
        reference.addValueEventListener(stateListener)
    }

    override fun onInactive() {
        super.onInactive()
        reference.addValueEventListener(stateListener)
    }

    private val stateListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val data = snapshot.getValue(cls)
            postValue(DataState.HasData(data ?: FirebaseCompletion.Default))
        }

        override fun onCancelled(error: DatabaseError) {
            postValue(DataState.HasError(error))
        }
    }
}