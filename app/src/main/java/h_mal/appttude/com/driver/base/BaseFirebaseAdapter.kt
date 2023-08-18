package h_mal.appttude.com.driver.base

import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError
import h_mal.appttude.com.driver.utils.GenericsHelper.getGenericClassAt
import h_mal.appttude.com.driver.utils.GenericsHelper.inflateBindingByType
import java.nio.ByteBuffer


open class BaseFirebaseAdapter<T : Any, VB : ViewBinding>(
    options: FirebaseRecyclerOptions<T>,
    private val layoutInflater: LayoutInflater
) :
    FirebaseRecyclerAdapter<T, CustomViewHolder<VB>>(options) {

    private val connectivityManager =
        layoutInflater.context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager

    private var _binding: VB? = null
    val binding: VB
        get() = _binding ?: error("Must only access binding while fragment is attached.")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<VB> {
        _binding = inflateBindingByType(getGenericClassAt(1), layoutInflater)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        binding.root.layoutParams = lp
        return CustomViewHolder(requireNotNull(_binding))
    }

    override fun onBindViewHolder(holder: CustomViewHolder<VB>, position: Int, model: T) {}

    override fun getItemId(position: Int): Long {
        return snapshots.getSnapshot(position).key?.toByteArray()
            ?.let { ByteBuffer.wrap(it).long } ?: super.getItemId(position)
    }

    fun getKeyAtPosition(position: Int) = snapshots.getSnapshot(position).key

    override fun startListening() {
        super.startListening()
        // check if network is connected
        if (connectivityManager.activeNetwork == null) {
            connectionLost()
        }
    }

    open fun connectionLost() {}
    override fun onDataChanged() {
        super.onDataChanged()
        if (itemCount == 0) emptyList()
    }

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

class CustomViewHolder<VB : ViewBinding>(val viewBinding: VB) : ViewHolder(viewBinding.root)