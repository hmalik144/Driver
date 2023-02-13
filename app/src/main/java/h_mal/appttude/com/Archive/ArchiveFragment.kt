package h_mal.appttude.com.Archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

import h_mal.appttude.com.R

class ArchiveFragment : Fragment() {
//    var archive: ArchiveObject? = null
    private var reference: DatabaseReference? = null
    private var listView: ListView? = null
    var archiveString: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        reference =
//            MainActivity.mDatabase!!.child(FirebaseClass.USER_FIREBASE).child(
//                requireArguments().getString("user_id")
//            )
//                .child(FirebaseClass.ARCHIVE_FIREBASE)

//        archiveString = requireArguments().getString("archive")
//        var s: String = ""
//        when (archiveString) {
//            FirebaseClass.PRIVATE_HIRE_FIREBASE -> s = "Private Hire"
//            FirebaseClass.DRIVERS_LICENSE_FIREBASE -> s = "License"
//            FirebaseClass.VEHICLE_DETAILS_FIREBASE -> s = "Vehicle"
//            FirebaseClass.MOT_FIREBASE -> s = "M.O.T"
//            FirebaseClass.INSURANCE_FIREBASE -> s = "Insurance"
//            FirebaseClass.LOG_BOOK_FIREBASE -> s = "Logbook"
//            FirebaseClass.PRIVATE_HIRE_VEHICLE_LICENSE -> s = "Private Hire Vehicle"
//        }
//        requireActivity().title = s + " Archive"
    }

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