package h_mal.appttude.com.driver.SuperUser

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
import h_mal.appttude.com.driver.Global.FirebaseClass
import h_mal.appttude.com.driver.MainActivity
import h_mal.appttude.com.driver.Objects.WholeDriverObject
import h_mal.appttude.com.driver.Objects.WholeObject.MappedObject
import h_mal.appttude.com.driver.R
import java.util.*


class homeSuperUserFragment : Fragment() {
    var users: DatabaseReference? = null
    var listViewSuperAdapter: ListViewSuperAdapter? = null
    var mappedObjectList: MutableList<MappedObject>? = null
    private var sharedPreferences: SharedPreferences? = null
    private var sortOrder: Int = 0
    private val sortDesc: Boolean = false
    private var recyclerView: RecyclerView? = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        users = MainActivity.mDatabase!!.child(FirebaseClass.USER_FIREBASE)
        mappedObjectList = ArrayList()
        MainActivity.viewController!!.progress(View.VISIBLE)
        users!!.addValueEventListener(valueEventListener)
        sharedPreferences = activity!!.getSharedPreferences("PREFS", 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home_super_user, container, false)

//        final ListView list = view.findViewById(R.id.list_view_super);

//        listViewSuperAdapter = new ListViewSuperAdapter(getContext(),mappedObjectList);
//        list.setAdapter(listViewSuperAdapter);
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.setLayoutManager(LinearLayoutManager(context))
        recyclerViewAdapter = RecyclerViewAdapter(context, mappedObjectList)
        recyclerView.setAdapter(recyclerViewAdapter)
        return view
    }

    var valueEventListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            mappedObjectList!!.clear()
            Log.i("Count ", "" + snapshot.childrenCount)
            for (postSnapshot: DataSnapshot in snapshot.children) {
                if ((postSnapshot.child("role").value.toString() == "driver")) {
                    MainActivity.printObjectAsJson("object", postSnapshot.toString())
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
            MainActivity.viewController!!.progress(View.GONE)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            MainActivity.viewController!!.progress(View.GONE)
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
            val compareInt: IntArray = intArrayOf(0)
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Sort by:")
                .setSingleChoiceItems(
                    grpname,
                    checkedItem,
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, item: Int) {
                            when (item) {
                                0 -> {
                                    compareInt.get(0) = 0
                                    return
                                }
                                1 -> {
                                    compareInt.get(0) = 1
                                    return
                                }
                                2 -> compareInt.get(0) = 2
                            }
                        }
                    }).setPositiveButton("Ascending", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id: Int) {
                        sortDate(compareInt.get(0), false)
                        dialog.dismiss()
                    }
                }).setNegativeButton("Descending", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id: Int) {
                        sortDate(compareInt.get(0), true)
                        dialog.dismiss()
                    }
                })
                .create().show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sortDate(compareInt: Int, reversed: Boolean) {
        Log.i(TAG, "sortDate: " + compareInt + " - " + reversed)
        val comparator: Comparator<MappedObject> = object : Comparator<MappedObject> {
            override fun compare(o1: MappedObject, o2: MappedObject): Int {
                when (compareInt) {
                    0 -> return o1.getWholeDriverObject().getUser_details().getProfileName()
                        .compareTo(
                            o2.getWholeDriverObject().getUser_details().getProfileName()
                        )
                    1 -> {
                        var s1: String? = o1.getWholeDriverObject().getDriver_number()
                        var s2: String? = o2.getWholeDriverObject().getDriver_number()
                        if (o1.getWholeDriverObject().driver_number == null || (o1.getWholeDriverObject()
                                .getDriver_number() == "0")
                        ) {
                            s1 = ";"
                        }
                        if (o2.getWholeDriverObject().driver_number == null || (o2.getWholeDriverObject()
                                .getDriver_number() == "0")
                        ) {
                            s2 = ";"
                        }
                        return s1!!.compareTo((s2)!!)
                    }
                    2 -> return MainActivity.approvalsClass!!.getOverApprovalStatusCode(o1.wholeDriverObject) -
                            MainActivity.approvalsClass!!.getOverApprovalStatusCode(o2.wholeDriverObject)
                    else -> return MainActivity.approvalsClass!!.getOverApprovalStatusCode(
                        o1.wholeDriverObject
                    ) -
                            MainActivity.approvalsClass!!.getOverApprovalStatusCode(o2.wholeDriverObject)
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
        //        listViewSuperAdapter.notifyDataSetChanged();
    }

    companion object {
        private val SORT: String = "SORT"
        private val REVERSED: String = "REVERSED"
        private val TAG: String = "homeSuperUserFragment"
    }
}