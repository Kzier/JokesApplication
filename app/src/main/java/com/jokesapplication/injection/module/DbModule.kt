package com.jokesapplication.injection.module

import android.content.Context
import androidx.room.Room
import com.jokesapplication.data.db.JokesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


private const val DbName = "jokes-database"

@Module
class DbModule {

    @Singleton
    @Provides
    fun provideDb(context: Context): JokesDatabase {
        return Room.databaseBuilder(context, JokesDatabase::class.java, DbName)
            /**
             * addMigrations( add some migration in future )
             */
            .build()
    }

}