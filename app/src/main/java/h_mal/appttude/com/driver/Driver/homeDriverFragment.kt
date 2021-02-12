package h_mal.appttude.com.driver.Driver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import h_mal.appttude.com.driver.Global.ExecuteFragment
import h_mal.appttude.com.driver.R


class homeDriverFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val button: Button = view.findViewById(R.id.driver)
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                ExecuteFragment.executeFragment(DriverOverallFragment())
            }
        })
        val second: CardView = view.findViewById(R.id.car)
        second.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                ExecuteFragment.executeFragment(VehicleOverallFragment())
            }
        })
        return view
    }
}