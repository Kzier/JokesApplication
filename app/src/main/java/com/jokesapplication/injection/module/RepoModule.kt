package com.jokesapplication.injection.module

import com.jokesapplication.data.repo.JokesRepo
import com.jokesapplication.data.repo.JokesRepoImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepoModule {

    @Singleton
    @Binds
    fun repository(impl: JokesRepoImpl): JokesRepo

}