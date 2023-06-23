package h_mal.appttude.com.driver.application

import h_mal.appttude.com.driver.data.prefs.PreferenceProvider
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class DriverApplication : BaseApplication() {

    override val flavourModule = super.flavourModule.copy {
        bind() from singleton { PreferenceProvider(this@DriverApplication) }
        bind() from provider {
            ApplicationViewModelFactory(
                instance(),
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
    }
}