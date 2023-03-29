package h_mal.appttude.com.driver.archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import h_mal.appttude.com.driver.R

class ArchiveFragment : Fragment() {
    //    var archive: ArchiveObject? = null
    private var reference: DatabaseReference? = null
    private var listView: ListView? = null
    var archiveString: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_archive, container, false)
        listView = view.findViewById(R.id.archive_listview)
//        reference!!.addListenerForSingleValueEvent(valueEventListener)
        return view
    }

//    var valueEventListener: ValueEventListener = object : ValueEventListener {
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//            archive = dataSnapshot.getValue(ArchiveObject::class.java)
//            listView!!.adapter = ArchiveObjectListAdapter(archive, requireContext(), archiveString)
//        }
//
//        override fun onCancelled(databaseError: DatabaseError) {}
//    }
}