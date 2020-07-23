package com.jokesapplication.data.api

import com.jokesapplication.data.Converter
import com.jokesapplication.data.response.ResponseJokeItem
import com.jokesapplication.domain.entity.Joke
import javax.inject.Inject

class ResponseConverter
@Inject
constructor() : Converter<ResponseJokeItem, Joke>() {

    override fun convert(input: ResponseJokeItem): Joke =
        Joke(input.id, input.joke)

    override fun convertBack(input: Joke): ResponseJokeItem {
        throw UnsupportedOperationException("Not implemented!!!")
    }

}