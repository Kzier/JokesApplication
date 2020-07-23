package com.jokesapplication.data.response

data class ResponseJokes(
    val type: String,
    val value: MutableList<ResponseJokeItem>
)

data class ResponseJokeItem(
    val id: Int,
    val joke: String
)