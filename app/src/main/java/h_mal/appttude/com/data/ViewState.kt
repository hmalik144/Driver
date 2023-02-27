package h_mal.appttude.com.data

import h_mal.appttude.com.utils.Event


sealed class ViewState {
    object HasStarted : ViewState()
    class HasData<T : Any>(val data: Event<T>) : ViewState()
    class HasError(val error: Event<String>) : ViewState()
}