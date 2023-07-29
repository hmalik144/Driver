package h_mal.appttude.com.driver.viewmodels

import h_mal.appttude.com.driver.base.DataViewerBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.model.PrivateHireLicense

class PrivateHireLicenseViewModel(
    private val database: FirebaseDatabaseSource
) : DataViewerBaseViewModel<PrivateHireLicense>() {

    override fun getDatabaseRef(uid: String) = database.getPrivateHireRef(uid)
}