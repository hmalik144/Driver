package h_mal.appttude.com.driver.viewmodels

import h_mal.appttude.com.driver.base.DataViewerBaseViewModel
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.model.Mot

class MotViewModel(
    private val database: FirebaseDatabaseSource
) : DataViewerBaseViewModel<Mot>() {

    override fun getDatabaseRef(uid: String) = database.getMotDetailsRef(uid)
}