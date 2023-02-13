package h_mal.appttude.com.SuperUser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import h_mal.appttude.com.Objects.WholeObject.MappedObject
import h_mal.appttude.com.R


class UserMainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_user_main, container, false)
        Log.i("UserMain", "onCreateView: height = " + view.height)
        val mappedObject: MappedObject? = requireArguments().getParcelable<MappedObject>("mapped")
        activity?.title = mappedObject?.wholeDriverObject?.user_details?.profileName

        val listView: GridView = view.findViewById(R.id.approvals_list)
        listView.adapter = ApprovalListAdapter(requireActivity(), arrayOf(mappedObject))
        return view
    }
}