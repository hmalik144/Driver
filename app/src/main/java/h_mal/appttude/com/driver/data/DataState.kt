package h_mal.appttude.com.driver.data

import com.google.firebase.database.DatabaseError


sealed class DataState {
    class HasData<T : Any>(val data: T) : DataState()
    class HasError(val error: DatabaseError) : DataState()
}