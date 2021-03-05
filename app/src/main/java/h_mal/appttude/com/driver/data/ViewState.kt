package h_mal.appttude.com.driver.data

import h_mal.appttude.com.driver.utils.Event


sealed class ViewState {
    object HasStarted : ViewState()
    class HasData<T : Any>(val data: Event<T>) : ViewState()
    class HasError(val error: Event<String>) : ViewState()
}