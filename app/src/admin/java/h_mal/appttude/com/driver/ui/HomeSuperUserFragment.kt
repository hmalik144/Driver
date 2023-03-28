package h_mal.appttude.com.driver.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import h_mal.appttude.com.driver.admin.objects.WholeDriverObject
import h_mal.appttude.com.driver.admin.objects.wholeObject.MappedObject
import h_mal.appttude.com.driver.R
import java.io.IOException
import java.util.*


class HomeSuperUserFragment : Fragment() {
    var users: DatabaseReference? = null
    var mappedObjectList: MutableList<MappedObject>? = null
    private var sharedPreferences: SharedPreferences? = null
    private var sortOrder: Int = 0
    private val sortDesc: Boolean = false
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mappedObjectList = ArrayList()
        users!!.addValueEventListener(valueEventListener)
        sharedPreferences = requireActivity().getSharedPreferences("PREFS", 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home_super_user, container, false)

        view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = RecyclerViewAdapter(context, mappedObjectList)
            adapter = recyclerViewAdapter
        }

        return view
    }

    var valueEventListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            mappedObjectList!!.clear()
            Log.i("Count ", "" + snapshot.childrenCount)
            for (postSnapshot: DataSnapshot in snapshot.children) {
                if ((postSnapshot.child("role").value.toString() == "driver")) {
                    mappedObjectList!!.add(
                        MappedObject(
                            postSnapshot.key, postSnapshot.getValue(
                                WholeDriverObject::class.java
                            )
                        )
                    )
                }
            }
            sortDate(sortOrder, sortDesc)

        }

        override fun onCancelled(databaseError: DatabaseError) {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_calls_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.archive) {
            val grpname: Array<String> = arrayOf("Driver Name", "Driver Number", "Approval")
            sortOrder = sharedPreferences!!.getInt(SORT, 0)
            val checkedItem: Int = sortOrder
            var compareInt = 0
            val click = DialogInterface.OnClickListener { dialog, _ ->
                sortDate(compareInt, false)
                dialog.dismiss()
            }
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Sort by:")
                .setSingleChoiceItems(
                    grpname,
                    checkedItem
                ) { _, pos -> compareInt = pos }
                .setPositiveButton("Ascending", click)
                .setNegativeButton("Descending", click)
                .create().show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sortDate(compareInt: Int, reversed: Boolean) {
        val comparator: Comparator<MappedObject> = object : Comparator<MappedObject> {
            override fun compare(o1: MappedObject, o2: MappedObject): Int {
                when (compareInt) {
                    0 -> return o1.wholeDriverObject?.user_details?.profileName!!.compareTo(
                        o2.wholeDriverObject?.user_details?.profileName!!
                    )
                    1 -> {
                        var s1: String? = o1.wholeDriverObject?.driver_number
                        var s2: String? = o2.wholeDriverObject?.driver_number
                        if (o1.wholeDriverObject?.driver_number == null || (o1.wholeDriverObject!!
                                .driver_number == "0")
                        ) {
                            s1 = ";"
                        }
                        if (o2.wholeDriverObject?.driver_number == null || (o2.wholeDriverObject!!
                                .driver_number == "0")
                        ) {
                            s2 = ";"
                        }
                        return s1!!.compareTo((s2)!!)
                    }
                    else -> {
                        throw IOException("dfdfs")
                    }
//                    2 -> return MainActivity.approvalsClass.getOverApprovalStatusCode(o1.wholeDriverObject) -
//                            MainActivity.approvalsClass.getOverApprovalStatusCode(o2.wholeDriverObject)
//                    else -> return MainActivity.approvalsClass.getOverApprovalStatusCode(
//                        o1.wholeDriverObject
//                    ) - MainActivity.approvalsClass.getOverApprovalStatusCode(o2.wholeDriverObject)
                }
            }
        }
        sharedPreferences!!.edit().putInt(SORT, compareInt).apply()
        sharedPreferences!!.edit().putBoolean(REVERSED, reversed).apply()
        if (reversed) {
            Collections.sort(mappedObjectList, comparator.reversed())
        } else {
            Collections.sort(mappedObjectList, comparator)
        }
        recyclerViewAdapter!!.notifyDataSetChanged()
    }

    companion object {
        private val SORT: String = "SORT"
        private val REVERSED: String = "REVERSED"
    }
}