package com.jokesapplication.data.db

import com.jokesapplication.data.Converter
import com.jokesapplication.data.db.entity.JokeDbObject
import com.jokesapplication.domain.entity.JokeLocal
import javax.inject.Inject

class DbModelConverter
@Inject
constructor() : Converter<JokeDbObject, JokeLocal>() {

    override fun convert(input: JokeDbObject): JokeLocal =
        JokeLocal(input.id!!, input.jokeId, input.text)

    override fun convertBack(input: JokeLocal): JokeDbObject =
        JokeDbObject(null, input.id, input.joke, System.currentTimeMillis())

}