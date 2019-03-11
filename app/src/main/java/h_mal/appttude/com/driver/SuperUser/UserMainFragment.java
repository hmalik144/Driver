package h_mal.appttude.com.driver.SuperUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import h_mal.appttude.com.driver.Archive.ArchiveFragment;
import h_mal.appttude.com.driver.Global.ExecuteFragment;
import h_mal.appttude.com.driver.MainActivity;
import h_mal.appttude.com.driver.Objects.WholeObject.MappedObject;
import h_mal.appttude.com.driver.R;

public class UserMainFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_main, container, false);

        Log.i("UserMain", "onCreateView: height = " + view.getHeight());

        MappedObject mappedObject = getArguments().getParcelable("mapped");

        getActivity().setTitle(mappedObject.getWholeDriverObject().getUser_details().getProfileName());

//        ListView listView = view.findViewById(R.id.approvals_list);
        GridView listView = view.findViewById(R.id.approvals_list);
        listView.setAdapter(new ApprovalListAdapter(getActivity(), new MappedObject[]{mappedObject}));

        return view;
    }


}
