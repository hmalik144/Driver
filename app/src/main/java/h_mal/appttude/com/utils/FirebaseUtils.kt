package h_mal.appttude.com.utils

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import h_mal.appttude.com.data.EventResponse
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
 *
 * @return T
 */
suspend inline fun <reified T : Any> DatabaseReference.getDataFromDatabaseRef(): T? {
    return when (val response: EventResponse = singleValueEvent()) {
        is EventResponse.Changed -> {
            response.snapshot.getValue(T::class.java)
        }
        is EventResponse.Cancelled -> {
            throw response.error.toException()
        }
    }
}