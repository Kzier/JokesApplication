package com.jokesapplication.presentation

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {

    /**
     * Dispatcher for network requests
     */
    fun io(): CoroutineDispatcher

    /**
     * Dispatcher for updating UI
     */
    fun main(): CoroutineDispatcher

    /**
     * Dispatcher that bounded to cpu core number
     */
    fun computation(): CoroutineDispatcher

    class CoroutineDispatcherProvider : DispatcherProvider {
        override fun io(): CoroutineDispatcher = Dispatchers.IO
        override fun main(): CoroutineDispatcher = Dispatchers.Main
        override fun computation(): CoroutineDispatcher = Dispatchers.Default
    }

}
