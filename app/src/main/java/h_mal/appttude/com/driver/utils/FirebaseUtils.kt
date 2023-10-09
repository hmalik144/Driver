package h_mal.appttude.com.driver.utils

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import h_mal.appttude.com.driver.data.DataState
import h_mal.appttude.com.driver.data.EventResponse
import h_mal.appttude.com.driver.data.FirebaseCompletion
import h_mal.appttude.com.driver.utils.GenericsHelper.getGenericClassAt
import java.lang.reflect.ParameterizedType
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Read database reference once {@link #DatabaseReference.addListenerForSingleValueEvent}
 *
 *
 * @return EventResponse
 */
suspend fun DatabaseReference.singleValueEvent(): EventResponse = suspendCoroutine { continuation ->
    val valueEventListener = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            continuation.resume(EventResponse.Cancelled(error))
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            continuation.resume(EventResponse.Changed(snapshot))
        }
    }
    addListenerForSingleValueEvent(valueEventListener)
}

/**
 * Read database reference once {@link #DatabaseReference.addListenerForSingleValueEvent}
 *
 * @return T
 */
suspend inline fun <reified T : Any> DatabaseReference.getDataFromDatabaseRef(): T? {
    return when (val response: EventResponse = singleValueEvent()) {
        is EventResponse.Changed -> response.snapshot.getValue(T::class.java)
        is EventResponse.Cancelled -> throw FirebaseException(response.error)
    }
}

/**
 * Read database reference once {@link #DatabaseReference.addListenerForSingleValueEvent}
 *
 * @return T
 */
suspend inline fun <reified T : Any> DatabaseReference.getListDataFromDatabaseRef(): List<T?> {
    return when (val response: EventResponse = singleValueEvent()) {
        is EventResponse.Changed -> {
            response.snapshot.children.map { it.getValue(T::class.java) }
        }

        is EventResponse.Cancelled -> {
            throw FirebaseException(response.error)
        }
    }
}

suspend fun <T : Any> DatabaseReference.getDataFromDatabaseRef(clazz: Class<T>): T? {
    return when (val response: EventResponse = singleValueEvent()) {
        is EventResponse.Changed -> {
            response.snapshot.getValue(clazz)
        }

        is EventResponse.Cancelled -> {
            throw FirebaseException(response.error)
        }
    }
}

fun <T : Any> DatabaseReference.toLiveData(): LiveData<DataState> {
    return object : LiveData<DataState>() {
        private val listener = addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(object : GenericTypeIndicator<T>() {})
                postValue(DataState.HasData(data ?: FirebaseCompletion.Default))
            }
            override fun onCancelled(error: DatabaseError) {
                postValue(DataState.HasError(error))
            }
        })
        override fun onActive() {
            super.onActive()
            // add listener
            addValueEventListener(listener)
        }

        override fun onInactive() {
            super.onInactive()
            // remove listener
            removeEventListener(listener)
        }
    }
}