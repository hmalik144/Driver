package h_mal.appttude.com.driver.viewmodels

import h_mal.appttude.com.driver.base.DataViewerBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.model.PrivateHireVehicle

class PrivateHireVehicleViewModel(
    private val database: FirebaseDatabaseSource
) : DataViewerBaseViewModel<PrivateHireVehicle>() {

    override fun getDatabaseRef(uid: String) = database.getPrivateHireVehicleRef(uid)
}