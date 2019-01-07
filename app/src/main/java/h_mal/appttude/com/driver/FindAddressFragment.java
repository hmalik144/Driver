package h_mal.appttude.com.driver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FindAddressFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_find_address, container, false);

        TextView findAddress = view.findViewById(R.id.findaddress);
        Button sumbit = view.findViewById(R.id.submit);

        LinearLayout linearLayout  = view.findViewById(R.id.lin_lay);

        EditText address = view.findViewById(R.id.address);
        EditText postcode  = view.findViewById(R.id.postcode);

        findAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelectorDialog dialogAddress = new ImageSelectorDialog(getContext());
                dialogAddress.show();
            }
        });

        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }


}
