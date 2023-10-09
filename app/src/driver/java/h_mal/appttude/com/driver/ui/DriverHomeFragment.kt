package h_mal.appttude.com.driver.ui

import android.os.Bundle
import android.view.View
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.base.BaseFragment
import h_mal.appttude.com.driver.data.DRIVER
import h_mal.appttude.com.driver.databinding.FragmentHomeDriverBinding
import h_mal.appttude.com.driver.utils.hide
import h_mal.appttude.com.driver.utils.navigateTo
import h_mal.appttude.com.driver.utils.show
import h_mal.appttude.com.driver.viewmodels.RoleViewModel


class DriverHomeFragment :
    BaseFragment<RoleViewModel, FragmentHomeDriverBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDataFromDatabase()
    }

    override fun onSuccess(data: Any?) {
        super.onSuccess(data)
        if (data == DRIVER) {
            loadDriver()
            return
        }
        loadNonDriver()
    }

    private fun loadNonDriver() {
        applyBinding {
            homeButtonsContainer.root.hide()
            profileRequestContainer.root.show()

            profileRequestContainer.requestDriverButton.setOnClickListener {
                viewModel.setDataInDatabase(DRIVER)
            }
        }
    }

    private fun loadDriver() {
        applyBinding {
            homeButtonsContainer.apply {
                driver.setOnClickListener {
                    view?.navigateTo(R.id.to_driverOverallFragment)
                }
                car.setOnClickListener {
                    view?.navigateTo(R.id.to_vehicleOverallFragment)
                }
                root.show()
            }
            profileRequestContainer.root.hide()
        }
    }

}