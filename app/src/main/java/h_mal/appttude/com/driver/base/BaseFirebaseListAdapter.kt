package h_mal.appttude.com.driver.base

import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import h_mal.appttude.com.driver.utils.GenericsHelper.getGenericClassAt
import h_mal.appttude.com.driver.utils.hide
import h_mal.appttude.com.driver.utils.show


abstract class BaseFirebaseListAdapter<T : Any>(
    options: FirebaseListOptions<T>
) : FirebaseListAdapter<T>(options) {

//    constructor(
//        query: Query,
//        @LayoutRes layout: Int,
//        lifecycleOwner: LifecycleOwner? = null
//    ): this(
//        FirebaseListOptions.Builder<T>()
//            .setQuery(query, this.getGenericClassAt<T>(0).java)
//            .setLayout(layout)
//            .setLifecycleOwner(lifecycleOwner)
//            .build()
//    )

    abstract val emptyView: View

    override fun startListening() {
        super.startListening()

        if (count == 0) {
            emptyView.show()
            emptyList()
        } else emptyView.hide()
    }

    //    private val connectivityManager =
//        layoutInflater.context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
//
//    private var _binding: VB? = null
//    val binding: VB
//        get() = _binding ?: error("Must only access binding while fragment is attached.")
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<VB> {
//        _binding = inflateBindingByType(getGenericClassAt(1), layoutInflater)
//        val lp = RecyclerView.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        binding.root.layoutParams = lp
//        return CustomViewHolder(requireNotNull(_binding))
//    }
//
//    override fun onBindViewHolder(holder: CustomViewHolder<VB>, position: Int, model: T) {}
//
//    override fun getItemId(position: Int): Long {
//        return snapshots.getSnapshot(position).key?.toByteArray()
//            ?.let { ByteBuffer.wrap(it).long } ?: super.getItemId(position)
//    }
//
    fun getKeyAtPosition(position: Int) = snapshots.getSnapshot(position).key
//
//    override fun startListening() {
//        super.startListening()
//        // check if network is connected
//        if (connectivityManager.activeNetwork == null) {
//            connectionLost()
//        }
//    }
//
//    open fun connectionLost() {}
//    override fun onDataChanged() {
//        super.onDataChanged()
//        if (itemCount == 0) emptyList()
//    }
//
    override fun onError(error: DatabaseError) {
        super.onError(error)
        when (error.code) {
            DatabaseError.PERMISSION_DENIED -> permissionsDenied()
            DatabaseError.DISCONNECTED, DatabaseError.UNAVAILABLE, DatabaseError.NETWORK_ERROR -> noConnection()
            DatabaseError.EXPIRED_TOKEN, DatabaseError.OPERATION_FAILED, DatabaseError.INVALID_TOKEN, DatabaseError.MAX_RETRIES -> authorizationError()
            else -> cannotRetrieve()
        }

    }

    open fun permissionsDenied() {}
    open fun noConnection() {}
    open fun cannotRetrieve() {}
    open fun authorizationError() {}
    open fun emptyList() {}
}

//class CustomViewHolder<VB : ViewBinding>(val viewBinding: VB) : ViewHolder(viewBinding.root)