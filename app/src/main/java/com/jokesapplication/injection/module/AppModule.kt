package com.jokesapplication.injection.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.jokesapplication.domain.preferences.PreferencesImpl
import com.jokesapplication.domain.preferences.PreferencesProvider
import com.jokesapplication.presentation.DispatcherProvider
import com.jokesapplication.presentation.common.shake.ShakeDetectorDelegate
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


private const val PREF_NAME = "app_preferences"

@Module
class AppModule {

    @Provides
    @Singleton
    fun providePreferences(
        application: Application
    ): SharedPreferences {
        return application.getSharedPreferences(
            PREF_NAME, Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun context(application: Application): Context = application

    @Provides
    @Singleton
    fun dispatcher(): DispatcherProvider = DispatcherProvider.CoroutineDispatcherProvider()

    @Provides
    @Singleton
    fun preferencesProvider(sp: SharedPreferences): PreferencesProvider = PreferencesImpl(sp)

    @Provides
    fun shakeDelegate() = ShakeDetectorDelegate()

}