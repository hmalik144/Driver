package h_mal.appttude.com.driver.viewmodels

import h_mal.appttude.com.driver.base.DataViewerBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.model.Insurance

class InsuranceViewModel(
    private val database: FirebaseDatabaseSource
) : DataViewerBaseViewModel<Insurance>() {

    override fun getDatabaseRef(uid: String) = database.getInsuranceDetailsRef(uid)
}