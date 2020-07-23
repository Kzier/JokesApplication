package com.jokesapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jokesapplication.presentation.common.event.SingleLiveEvent
import kotlinx.coroutines.*

open class BaseViewModel(
    protected val dispatcher: DispatcherProvider
) : ViewModel() {

    private val errorLiveData = SingleLiveEvent<Throwable>()

    val error: LiveData<Throwable> = errorLiveData

    protected fun <T> startCoroutine(
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        block: suspend CoroutineScope.() -> T
    ) =
        viewModelScope.launch(dispatcher) {
            block()
        }

    protected fun <T> runCoroutine(
        dispatcher: CoroutineDispatcher = this.dispatcher.main(),
        block: suspend CoroutineScope.() -> T
    ) =
        viewModelScope.launch(dispatcher) {
            try {
                block()
            } catch (t: Throwable) {
                if (t !is CancellationException) {
                    errorLiveData.postValue(t)
                } else throw t
            }

        }

}