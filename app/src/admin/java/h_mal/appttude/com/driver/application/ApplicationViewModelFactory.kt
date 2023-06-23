package h_mal.appttude.com.driver.application

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import h_mal.appttude.com.driver.data.FirebaseAuthSource
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.prefs.PreferenceProvider
import h_mal.appttude.com.driver.viewmodels.*

class ApplicationViewModelFactory(
    private val auth: FirebaseAuthSource,
    private val database: FirebaseDatabaseSource,
    private val storage: FirebaseStorageSource,
    private val preferences: PreferenceProvider,
    private val resources: Resources
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        with(modelClass) {
            return when {
                isAssignableFrom(UserViewModel::class.java) -> UserViewModel(auth)
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel(auth, database)
                isAssignableFrom(UpdateUserViewModel::class.java) -> UpdateUserViewModel(
                    auth,
                    storage
                )
                isAssignableFrom(RoleViewModel::class.java) -> RoleViewModel(
                    auth,
                    database,
                    storage
                )

                isAssignableFrom(DriverLicenseViewModel::class.java) -> DriverLicenseViewModel(
                    database
                )
                isAssignableFrom(DriverProfileViewModel::class.java) -> DriverProfileViewModel(
                    database
                )
                isAssignableFrom(PrivateHireLicenseViewModel::class.java) -> PrivateHireLicenseViewModel(
                    database
                )
                isAssignableFrom(VehicleProfileViewModel::class.java) -> VehicleProfileViewModel(
                    database
                )
                isAssignableFrom(InsuranceViewModel::class.java) -> InsuranceViewModel(database)
                isAssignableFrom(MotViewModel::class.java) -> MotViewModel(database)
                isAssignableFrom(LogbookViewModel::class.java) -> LogbookViewModel(database)
                isAssignableFrom(PrivateHireVehicleViewModel::class.java) -> PrivateHireVehicleViewModel(
                    database
                )
                isAssignableFrom(DriverOverviewViewModel::class.java) -> DriverOverviewViewModel(
                    auth,
                    database
                )
                isAssignableFrom(SuperUserViewModel::class.java) -> SuperUserViewModel(
                    database,
                    preferences
                )
                isAssignableFrom(ApproverViewModel::class.java) -> ApproverViewModel(resources , database)
                else -> throw IllegalArgumentException("Unknown ViewModel class")
            } as T
        }
    }

}