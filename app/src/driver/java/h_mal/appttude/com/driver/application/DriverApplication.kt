package h_mal.appttude.com.driver.application

import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

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