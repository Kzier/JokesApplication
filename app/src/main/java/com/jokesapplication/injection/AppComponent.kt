package com.jokesapplication.injection

import android.app.Application
import com.jokesapplication.injection.module.*
import com.jokesapplication.presentation.local.LocalJokesFragment
import com.jokesapplication.presentation.remote.JokesFragment
import com.jokesapplication.presentation.settings.SettingsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class,
        DbModule::class,
        ApiModule::class,
        ViewModelModule::class,
        ViewModelFactoryModule::class,
        ConverterModule::class,
        RepoModule::class
    ]
)
interface AppComponent {

    fun inject(fragment: JokesFragment)
    fun inject(fragment: LocalJokesFragment)
    fun inject(fragment: SettingsFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}