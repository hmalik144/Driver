package h_mal.appttude.com.driver.viewmodels

import h_mal.appttude.com.driver.base.DataViewerBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.model.DriversLicense

class DriverLicenseViewModel(
    private val database: FirebaseDatabaseSource
) : DataViewerBaseViewModel<DriversLicense>() {

    override fun getDatabaseRef(uid: String) = database.getDriverLicenseRef(uid)
}