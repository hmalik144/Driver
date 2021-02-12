package h_mal.appttude.com.driver.SuperUser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import h_mal.appttude.com.driver.Objects.WholeObject.MappedObject
import h_mal.appttude.com.driver.R


class UserMainFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_user_main, container, false)
        Log.i("UserMain", "onCreateView: height = " + view.height)
        val mappedObject: MappedObject = arguments!!.getParcelable("mapped")
        activity.setTitle(
            mappedObject.getWholeDriverObject().getUser_details().getProfileName()
        )

//        ListView listView = view.findViewById(R.id.approvals_list);
        val listView: GridView = view.findViewById(R.id.approvals_list)
        listView.adapter = ApprovalListAdapter((activity)!!, arrayOf(mappedObject))
        return view
    }
}