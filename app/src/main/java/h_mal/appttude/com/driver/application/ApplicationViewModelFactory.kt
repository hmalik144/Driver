package h_mal.appttude.com.driver.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import h_mal.appttude.com.driver.data.FirebaseAuthSource
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.viewmodels.*

class ApplicationViewModelFactory(
    private val firebaseAuthSource: FirebaseAuthSource,
    private val firebaseDatabaseSource: FirebaseDatabaseSource,
    private val firebaseStorageSource: FirebaseStorageSource
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        with(modelClass){
            return when{
                isAssignableFrom(UserViewModel::class.java) -> UserViewModel(firebaseAuthSource)
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel(firebaseAuthSource, firebaseDatabaseSource)
                isAssignableFrom(UpdateUserViewModel::class.java) -> UpdateUserViewModel(firebaseAuthSource, firebaseStorageSource)
                isAssignableFrom(DriverLicenseViewModel::class.java) -> DriverLicenseViewModel(firebaseAuthSource, firebaseDatabaseSource, firebaseStorageSource)
                isAssignableFrom(DriverProfileViewModel::class.java) -> DriverProfileViewModel(firebaseAuthSource, firebaseDatabaseSource, firebaseStorageSource)
                isAssignableFrom(PrivateHireLicenseViewModel::class.java) -> PrivateHireLicenseViewModel(firebaseAuthSource, firebaseDatabaseSource, firebaseStorageSource)
                isAssignableFrom(VehicleProfileViewModel::class.java) -> VehicleProfileViewModel(firebaseAuthSource, firebaseDatabaseSource, firebaseStorageSource)
                isAssignableFrom(InsuranceViewModel::class.java) -> InsuranceViewModel(firebaseAuthSource, firebaseDatabaseSource, firebaseStorageSource)
                isAssignableFrom(MotViewModel::class.java) -> MotViewModel(firebaseAuthSource, firebaseDatabaseSource, firebaseStorageSource)
                isAssignableFrom(LogbookViewModel::class.java) -> LogbookViewModel(firebaseAuthSource, firebaseDatabaseSource, firebaseStorageSource)
                isAssignableFrom(PrivateHireVehicleViewModel::class.java) -> PrivateHireVehicleViewModel(firebaseAuthSource, firebaseDatabaseSource, firebaseStorageSource)
                else -> throw IllegalArgumentException("Unknown ViewModel class")
            } as T
        }
    }

}