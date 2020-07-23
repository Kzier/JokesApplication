package com.jokesapplication.injection.module

import androidx.lifecycle.ViewModelProvider
import com.jokesapplication.presentation.AppViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ViewModelFactoryModule {

    @Singleton
    @Binds
    fun factory(impl: AppViewModelFactory): ViewModelProvider.Factory
}