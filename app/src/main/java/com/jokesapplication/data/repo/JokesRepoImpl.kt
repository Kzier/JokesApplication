package com.jokesapplication.data.repo

import com.jokesapplication.data.Converter
import com.jokesapplication.data.api.JokeApi
import com.jokesapplication.data.db.JokesDatabase
import com.jokesapplication.data.db.entity.JokeDbObject
import com.jokesapplication.data.response.ResponseJokeItem
import com.jokesapplication.domain.entity.Joke
import com.jokesapplication.domain.entity.JokeLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject


class JokesRepoImpl
@Inject
constructor(
    private val api: JokeApi,
    private val apiConverter: Converter<ResponseJokeItem, Joke>,
    private val db: JokesDatabase,
    private val dbConverter: Converter<JokeDbObject, JokeLocal>
) : JokesRepo {

    override suspend fun getJokes(firstName: String?, lastName: String?): MutableList<Joke> {
        return apiConverter.convertList(api.getJokes(firstName, lastName).value)
    }

    override fun getLocalJokesFlow(): Flow<MutableList<JokeLocal>> {
        return getLocalDao().getJokesFlow()
            .transform { emit(dbConverter.convertList(it)) }
    }

    override suspend fun likeJoke(joke: Joke) {
        getLocalDao().insertItem(JokeDbObject(null, joke.id, joke.joke, System.currentTimeMillis()))
    }

    override suspend fun deleteJoke(joke: JokeLocal) {
        getLocalDao().deleteJoke(joke.persistentId)
    }

    override suspend fun addCustomJoke(jokeString: String) {
        getLocalDao().insertItem(JokeDbObject(null, null, jokeString, System.currentTimeMillis()))
    }

    override suspend fun getRandomJoke(): MutableList<JokeLocal> =
        dbConverter.convertList(getLocalDao().getRandomJoke())

    private fun getLocalDao() = db.getJokeDao()

}