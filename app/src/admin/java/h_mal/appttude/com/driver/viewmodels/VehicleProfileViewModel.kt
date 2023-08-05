package h_mal.appttude.com.driver.viewmodels

import h_mal.appttude.com.driver.base.DataViewerBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.model.VehicleProfile

class VehicleProfileViewModel(
    private val database: FirebaseDatabaseSource
) : DataViewerBaseViewModel<VehicleProfile>() {

    override fun getDatabaseRef(uid: String) = database.getVehicleDetailsRef(uid)
}