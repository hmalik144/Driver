package h_mal.appttude.com.ui

import android.os.Bundle
import android.view.View
import h_mal.appttude.com.R
import h_mal.appttude.com.base.DataSubmissionBaseFragment
import h_mal.appttude.com.data.DRIVER
import h_mal.appttude.com.databinding.FragmentHomeDriverBinding
import h_mal.appttude.com.utils.hide
import h_mal.appttude.com.utils.navigateTo
import h_mal.appttude.com.utils.show
import h_mal.appttude.com.viewmodels.RoleViewModel


class HomeFragment :
    DataSubmissionBaseFragment<RoleViewModel, FragmentHomeDriverBinding, String>() {

    override var model = String()

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