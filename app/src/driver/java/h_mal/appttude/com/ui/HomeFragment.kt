package h_mal.appttude.com.ui

import android.os.Bundle
import android.view.View
import h_mal.appttude.com.R
import h_mal.appttude.com.base.DataSubmissionBaseFragment
import h_mal.appttude.com.data.DRIVER
import h_mal.appttude.com.utils.hide
import h_mal.appttude.com.utils.navigateTo
import h_mal.appttude.com.utils.show
import h_mal.appttude.com.viewmodels.RoleViewModel
import kotlinx.android.synthetic.main.driver_profile_request.*

import kotlinx.android.synthetic.main.fragment_home_driver.*
import kotlinx.android.synthetic.main.home_buttons_container.*
import kotlinx.android.synthetic.main.home_buttons_container.driver


class HomeFragment : DataSubmissionBaseFragment<RoleViewModel, String>(R.layout.fragment_home_driver) {

    private val viewmodel: RoleViewModel by getFragmentViewModel()
    override fun getViewModel(): RoleViewModel = viewmodel
    override var model = String()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.getDataFromDatabase()
    }

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        if (data == DRIVER){
            loadDriver()
            return
        }
        loadNonDriver()
    }

    private fun loadNonDriver(){
        home_buttons_container.hide()
        profile_request_container.show()

        request_driver_button.setOnClickListener {
            viewmodel.setDataInDatabase(DRIVER)
        }
    }

    private fun loadDriver(){
        home_buttons_container.show()
        profile_request_container.hide()

        driver.setOnClickListener {
            view?.navigateTo(R.id.to_driverOverallFragment)
        }
        car.setOnClickListener {
            view?.navigateTo(R.id.to_vehicleOverallFragment)
        }
    }

}