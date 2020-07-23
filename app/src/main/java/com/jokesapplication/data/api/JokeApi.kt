package com.jokesapplication.data.api

import com.jokesapplication.data.response.ResponseJokes
import retrofit2.http.GET
import retrofit2.http.Query

interface JokeApi {

    @GET("/jokes")
    suspend fun getJokes(@Query("firstName") firstName: String?, @Query("lastName") lastName: String?): ResponseJokes

}