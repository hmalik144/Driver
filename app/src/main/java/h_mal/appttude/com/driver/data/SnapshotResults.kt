package h_mal.appttude.com.driver.data

import com.google.firebase.database.DataSnapshot
import h_mal.appttude.com.driver.utils.GenericsHelper.getGenericClassAt

class SnapshotResults<T: Any>(
    private val snapshot: DataSnapshot
) {
    private val cls = this.getGenericClassAt<T>(0).getGenericClassAt<T>(0).java

    fun getData(): T? {
        return snapshot.getValue(cls)
    }
}