package com.jokesapplication.presentation.local

import androidx.lifecycle.viewModelScope
import com.jokesapplication.data.repo.JokesRepo
import com.jokesapplication.domain.entity.Joke
import com.jokesapplication.domain.entity.JokeLocal
import com.jokesapplication.presentation.BaseViewModel
import com.jokesapplication.presentation.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocalJokesViewModel
@Inject constructor(private val repo: JokesRepo, dispatcherProvider: DispatcherProvider) :
    BaseViewModel(dispatcherProvider) {

    init {
        observeLocalJokes()
    }

    private fun observeLocalJokes() {
        viewModelScope.launch {
            repo.getLocalJokesFlow()
                .collect {
                    localJokes.value = it.toMutableList()
                }
        }
    }

    val localJokes = MutableStateFlow<MutableList<Joke>>(mutableListOf())

    fun addJoke(joke: String) {
        runCoroutine(dispatcher.io()) {
            repo.addCustomJoke(joke)
        }
    }

    fun deleteJoke(obj: JokeLocal) {
        runCoroutine(dispatcher.io()) {
            repo.deleteJoke(obj)
        }
    }


}