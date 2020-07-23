package com.jokesapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jokesapplication.data.db.dao.JokeDao
import com.jokesapplication.data.db.entity.JokeDbObject


@Database(
    entities = [JokeDbObject::class],
    version = 1,
    exportSchema = false
)
abstract class JokesDatabase : RoomDatabase() {

    abstract fun getJokeDao(): JokeDao

}