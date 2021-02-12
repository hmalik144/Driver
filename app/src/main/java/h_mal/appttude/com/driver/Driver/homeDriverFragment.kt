package h_mal.appttude.com.driver.Driver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import h_mal.appttude.com.driver.Global.ExecuteFragment;
import h_mal.appttude.com.driver.R;

import static h_mal.appttude.com.driver.Global.ExecuteFragment.executeFragment;


public class homeDriverFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        Button button = view.findViewById(R.id.driver);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeFragment(new DriverOverallFragment());
            }
        });

        CardView second= view.findViewById(R.id.car);
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeFragment(new VehicleOverallFragment());
            }
        });

        return view;
    }



}
