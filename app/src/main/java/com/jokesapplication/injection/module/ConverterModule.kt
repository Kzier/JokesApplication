package com.jokesapplication.injection.module

import com.jokesapplication.data.Converter
import com.jokesapplication.data.api.ResponseConverter
import com.jokesapplication.data.db.DbModelConverter
import com.jokesapplication.data.db.entity.JokeDbObject
import com.jokesapplication.data.response.ResponseJokeItem
import com.jokesapplication.domain.entity.Joke
import com.jokesapplication.domain.entity.JokeLocal
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ConverterModule {

    @Singleton
    @Binds
    fun responseConverter(impl: ResponseConverter): Converter<ResponseJokeItem, Joke>

    @Singleton
    @Binds
    fun dbConverter(impl: DbModelConverter): Converter<JokeDbObject, JokeLocal>

}