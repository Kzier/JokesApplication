package com.jokesapplication.injection.module

import androidx.lifecycle.ViewModel
import com.jokesapplication.injection.ViewModelKey
import com.jokesapplication.presentation.local.LocalJokesViewModel
import com.jokesapplication.presentation.remote.JokeViewModel
import com.jokesapplication.presentation.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(JokeViewModel::class)
    abstract fun bindMainViewModel(viewModel: JokeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocalJokesViewModel::class)
    abstract fun bindLocalJokesViewModel(viewModel: LocalJokesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

}