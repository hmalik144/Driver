package h_mal.appttude.com.driver.SuperUser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import h_mal.appttude.com.driver.Objects.WholeObject.MappedObject;
import h_mal.appttude.com.driver.Objects.WholeDriverObject;
import h_mal.appttude.com.driver.R;

import static h_mal.appttude.com.driver.Global.FirebaseClass.USER_FIREBASE;
import static h_mal.appttude.com.driver.MainActivity.approvalsClass;
import static h_mal.appttude.com.driver.MainActivity.mDatabase;
import static h_mal.appttude.com.driver.MainActivity.printObjectAsJson;
import static h_mal.appttude.com.driver.MainActivity.viewController;


public class homeSuperUserFragment extends Fragment {

    DatabaseReference users;
    ListViewSuperAdapter listViewSuperAdapter;

    List<MappedObject> mappedObjectList;
    private SharedPreferences sharedPreferences;
    private int sortOrder;
    private boolean sortDesc;
    private static final String SORT = "SORT";
    private static final String REVERSED = "REVERSED";
    private static final String TAG = "homeSuperUserFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        users = mDatabase.child(USER_FIREBASE);
        mappedObjectList = new ArrayList<>();

        viewController.progress(View.VISIBLE);
        users.addValueEventListener(valueEventListener);

        sharedPreferences = getActivity().getSharedPreferences("PREFS", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_super_user, container, false);

        final ListView list = view.findViewById(R.id.list_view_super);

        listViewSuperAdapter = new ListViewSuperAdapter(getContext(),mappedObjectList);
        list.setAdapter(listViewSuperAdapter);

        return view;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            mappedObjectList.clear();
            Log.i("Count " ,""+snapshot.getChildrenCount());
            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                if (postSnapshot.child("role").getValue().toString().equals("driver")){
                    printObjectAsJson("object",postSnapshot.toString());
                    mappedObjectList.add(new MappedObject(postSnapshot.getKey(),postSnapshot.getValue(WholeDriverObject.class)));
                }
            }
            sortDate(sortOrder,sortDesc);
            viewController.progress(View.GONE);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            viewController.progress(View.GONE);
        }


    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_calls_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.archive){
            final String[] grpname = {"Driver Name","Driver Number","Approval"};
            sortOrder = sharedPreferences.getInt(SORT,0);
            int checkedItem = sortOrder;
            final int[] compareInt = {0};

            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Sort by:")
                    .setSingleChoiceItems(grpname, checkedItem, new DialogInterface
                    .OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    switch (item) {
                        case 0:
                            compareInt[0] = 0;
                            return;
                        case 1:
                            compareInt[0] = 1;
                            return;
                        case 2:
                            compareInt[0] = 2;
                    }
                }
            }).setPositiveButton("Ascending", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    sortDate(compareInt[0],false);
                    dialog.dismiss();
                }
            }).setNegativeButton("Descending", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    sortDate(compareInt[0],true);
                    dialog.dismiss();
                }
            })
            .create().show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void sortDate(final int compareInt, boolean reversed){
        Log.i(TAG, "sortDate: " + compareInt + " - " + reversed);
        Comparator<MappedObject> comparator = new Comparator<MappedObject>() {
                @Override
                public int compare(MappedObject o1, MappedObject o2) {
                    switch (compareInt){
                        case 0:
                            return o1.getWholeDriverObject().getUser_details().getProfileName().compareTo(
                                    o2.getWholeDriverObject().getUser_details().getProfileName());
                        case 1:
                            return o1.getWholeDriverObject().getDriver_number()
                                    .compareTo(o2.getWholeDriverObject().getDriver_number());
                        case 2:
                            return approvalsClass.getOverApprovalStatusCode(o1.wholeDriverObject) -
                                    approvalsClass.getOverApprovalStatusCode(o2.wholeDriverObject);
                        default:
                            return approvalsClass.getOverApprovalStatusCode(o1.wholeDriverObject) -
                                    approvalsClass.getOverApprovalStatusCode(o2.wholeDriverObject);
                    }
                }
            };

        sharedPreferences.edit().putInt(SORT,compareInt).apply();
        sharedPreferences.edit().putBoolean(REVERSED,reversed).apply();

        if (reversed){
            Collections.sort(mappedObjectList,comparator.reversed());
        }else {
            Collections.sort(mappedObjectList,comparator);
        }

        listViewSuperAdapter.notifyDataSetChanged();
    }
}
