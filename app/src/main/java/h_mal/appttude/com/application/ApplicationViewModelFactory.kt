package h_mal.appttude.com.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import h_mal.appttude.com.data.FirebaseAuthSource
import h_mal.appttude.com.data.FirebaseDatabaseSource
import h_mal.appttude.com.data.FirebaseStorageSource
import h_mal.appttude.com.viewmodels.*

class ApplicationViewModelFactory(
    private val auth: FirebaseAuthSource,
    private val database: FirebaseDatabaseSource,
    private val storage: FirebaseStorageSource
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        with(modelClass){
            return when{
                isAssignableFrom(UserViewModel::class.java) -> UserViewModel(auth)
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel(auth, database)
                isAssignableFrom(UpdateUserViewModel::class.java) -> UpdateUserViewModel(auth, storage)
                isAssignableFrom(DriverLicenseViewModel::class.java) -> DriverLicenseViewModel(auth, database, storage)
                isAssignableFrom(DriverProfileViewModel::class.java) -> DriverProfileViewModel(auth, database, storage)
                isAssignableFrom(PrivateHireLicenseViewModel::class.java) -> PrivateHireLicenseViewModel(auth, database, storage)
                isAssignableFrom(VehicleProfileViewModel::class.java) -> VehicleProfileViewModel(auth, database, storage)
                isAssignableFrom(InsuranceViewModel::class.java) -> InsuranceViewModel(auth, database, storage)
                isAssignableFrom(MotViewModel::class.java) -> MotViewModel(auth, database, storage)
                isAssignableFrom(LogbookViewModel::class.java) -> LogbookViewModel(auth, database, storage)
                isAssignableFrom(PrivateHireVehicleViewModel::class.java) -> PrivateHireVehicleViewModel(auth, database, storage)
                isAssignableFrom(RoleViewModel::class.java) -> RoleViewModel(auth, database, storage)
                else -> throw IllegalArgumentException("Unknown ViewModel class")
            } as T
        }
    }

}