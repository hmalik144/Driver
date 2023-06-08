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

class DriverApplication : Application(), KodeinAware {

    // Kodein aware to initialise the classes used for DI
    override val kodein = Kodein.lazy {
        import(androidXModule(this@DriverApplication))
        bind() from singleton { FirebaseAuthSource() }
        bind() from singleton { FirebaseDatabaseSource() }
        bind() from singleton { FirebaseStorageSource() }
        bind() from singleton { PreferenceProvider(this@DriverApplication) }
        bind() from provider { ApplicationViewModelFactory(instance(), instance(), instance(), instance(), instance<Resources>()) }
    }
}