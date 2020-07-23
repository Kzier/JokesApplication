package com.jokesapplication.domain.entity

open class Joke(val id: Int?, val joke: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Joke) return false

        if (id != other.id) return false
        if (joke != other.joke) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + joke.hashCode()
        return result
    }

}
