package com.jokesapplication.presentation.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jokesapplication.data.repo.JokesRepo
import com.jokesapplication.domain.entity.Joke
import com.jokesapplication.domain.preferences.PreferencesProvider
import com.jokesapplication.presentation.BaseViewModel
import com.jokesapplication.presentation.DispatcherProvider
import javax.inject.Inject

class JokeViewModel
@Inject
constructor(
    private val repo: JokesRepo,
    private val preferencesProvider: PreferencesProvider,
    dispatcher: DispatcherProvider
) : BaseViewModel(dispatcher) {

    val jokes = MutableLiveData<MutableList<out Joke>>()

    init {
        refresh()
    }

    private fun refresh() {
        if (!preferencesProvider.isOffline()) {
            runCoroutine {
                jokes.postValue(
                    repo.getJokes(
                        preferencesProvider.getFirstName(),
                        preferencesProvider.getLastName()
                    )
                )
            }
        }
    }

    fun likeJoke(obj: Joke) = runCoroutine { repo.likeJoke(obj) }

    fun random() {
        if(preferencesProvider.isOffline()) {
            runCoroutine {
                val joke = repo.getRandomJoke()
                Log.d("JokeViewModel", joke.toString())
                jokes.postValue(joke)
            }
        }
    }

}