package com.jokesapplication.injection.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jokesapplication.data.api.JokeApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val DEFAULT_READ_TIME_OUT = 30L
const val DEFAULT_WRITE_TIME_OUT = 30L
const val BASE_URL = "https://api.icndb.com"

@Module
class ApiModule {

    @Singleton
    @Provides
    fun client(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    @Singleton
    @Provides
    fun retrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
        .build()

    @Singleton
    @Provides
    fun api(retrofit: Retrofit): JokeApi = retrofit.create(JokeApi::class.java)

}