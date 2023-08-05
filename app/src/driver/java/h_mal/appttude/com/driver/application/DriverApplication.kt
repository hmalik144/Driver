package h_mal.appttude.com.driver.application

import android.app.Application
import android.content.res.Resources
import h_mal.appttude.com.driver.data.FirebaseAuthSource
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import h_mal.appttude.com.driver.data.prefs.PreferenceProvider
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class DriverApplication : BaseApplication() {

    override val flavourModule = super.flavourModule.copy {
        bind() from provider {
            ApplicationViewModelFactory(
                instance(),
                instance(),
                instance()
            )
        }
    }
}