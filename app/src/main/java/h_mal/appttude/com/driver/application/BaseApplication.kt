package h_mal.appttude.com.driver.application

import android.app.Application
import h_mal.appttude.com.driver.data.FirebaseAuthSource
import h_mal.appttude.com.driver.data.FirebaseDatabaseSource
import h_mal.appttude.com.driver.data.FirebaseStorageSource
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

open class BaseApplication : Application(), KodeinAware {

    // Kodein aware to initialise the classes used for DI
    override val kodein = Kodein.lazy {
        import(parentModule)
        import(flavourModule)
    }

    val parentModule = Kodein.Module("Parent Module") {
        import(androidXModule(this@BaseApplication))
        bind() from singleton { FirebaseAuthSource() }
        bind() from singleton { FirebaseDatabaseSource() }
        bind() from singleton { FirebaseStorageSource() }
    }

    open val flavourModule = Kodein.Module("Flavour") {
        import(parentModule)
    }
}