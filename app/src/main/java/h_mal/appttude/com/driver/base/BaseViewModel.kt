package h_mal.appttude.com.driver.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import h_mal.appttude.com.driver.data.ViewState
import h_mal.appttude.com.driver.utils.Event

abstract class BaseViewModel : ViewModel() {
    open val uiState: MutableLiveData<ViewState> = MutableLiveData()

    fun onStart() {
        uiState.postValue(ViewState.HasStarted)
    }

    fun <T : Any> onSuccess(result: T) {
        uiState.postValue(ViewState.HasData(Event(result)))
    }

    protected fun onError(error: String) {
        uiState.postValue(ViewState.HasError(Event(error)))
    }

    suspend fun doTryOperation(
        defaultErrorMessage: String?,
        operation: suspend () -> Unit
    ) {
        try {
            onStart()
            operation()
        } catch (e: Exception) {
            e.printStackTrace()
            defaultErrorMessage?.let {
                onError(it)
                return
            }
            onError((e.message ?: "Operation failed!!"))
        }
    }
}