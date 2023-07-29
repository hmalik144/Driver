package h_mal.appttude.com.driver.viewmodels

import h_mal.appttude.com.driver.base.DataViewerBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.model.DriverProfile

class DriverProfileViewModel(
    private val database: FirebaseDatabaseSource
) : DataViewerBaseViewModel<DriverProfile>() {
    override fun getDatabaseRef(uid: String) = database.getDriverDetailsRef(uid)
}