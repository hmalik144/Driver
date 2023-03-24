package h_mal.appttude.com.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import h_mal.appttude.com.R
import h_mal.appttude.com.databinding.FragmentDriverOverallBinding
import h_mal.appttude.com.utils.navigateTo

class DriverOverallFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentDriverOverallBinding.inflate(inflater, container, false).apply {
            driverProf.setOnClickListener {
                it.navigateTo(R.id.to_driverProfileFragment)
            }
            privateHire.setOnClickListener {
                it.navigateTo(R.id.to_privateHireLicenseFragment2)
            }
            driversLicense.setOnClickListener {
                it.navigateTo(R.id.to_driverLicenseFragment)
            }
        }.root
    }

}