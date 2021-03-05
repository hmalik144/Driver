package h_mal.appttude.com.driver.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.utils.navigateTo

import kotlinx.android.synthetic.main.fragment_home_driver.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home_driver, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        driver.setOnClickListener {
            view.navigateTo(R.id.to_driverOverallFragment)
        }
        car.setOnClickListener {
            view.navigateTo(R.id.to_vehicleOverallFragment)
        }
    }
}