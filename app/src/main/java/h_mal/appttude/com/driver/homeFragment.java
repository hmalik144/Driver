package h_mal.appttude.com.driver;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static h_mal.appttude.com.driver.MainActivity.auth;
import static h_mal.appttude.com.driver.MainActivity.executeFragment;
import static h_mal.appttude.com.driver.MainActivity.fragmentManager;


public class homeFragment extends Fragment {

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
                executeFragment(new driverProfileFragment());
            }
        });

        CardView second= view.findViewById(R.id.car);
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeFragment(new VehicleSetupFragment());
            }
        });

        return view;
    }



}
