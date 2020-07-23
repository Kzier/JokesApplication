package com.jokesapplication.data.repo

import com.jokesapplication.domain.entity.Joke
import com.jokesapplication.domain.entity.JokeLocal
import kotlinx.coroutines.flow.Flow

interface JokesRepo {

    suspend fun getJokes(firstName: String?, lastName: String?): MutableList<Joke>

    suspend fun likeJoke(joke: Joke)

    fun getLocalJokesFlow(): Flow<MutableList<JokeLocal>>

    suspend fun deleteJoke(joke: JokeLocal)

    suspend fun addCustomJoke(jokeString: String)

    suspend fun getRandomJoke(): MutableList<JokeLocal>

}