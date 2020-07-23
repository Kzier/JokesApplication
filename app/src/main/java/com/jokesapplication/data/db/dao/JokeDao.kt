package com.jokesapplication.data.db.dao

import androidx.room.*
import com.jokesapplication.data.db.entity.JOKE_TABLE_NAME
import com.jokesapplication.data.db.entity.JokeDbObject
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(list: MutableList<JokeDbObject>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(obj: JokeDbObject)

    @Transaction
    @Query("DELETE FROM $JOKE_TABLE_NAME")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM $JOKE_TABLE_NAME ORDER BY timestamp")
    suspend fun getJokes(): MutableList<JokeDbObject>

    @Transaction
    @Query("SELECT * FROM $JOKE_TABLE_NAME ORDER BY timestamp")
    fun getJokesFlow(): Flow<MutableList<JokeDbObject>>

    @Transaction
    @Query("DELETE FROM $JOKE_TABLE_NAME WHERE id IS :id")
    suspend fun deleteJoke(id: Long)

    @Transaction
    @Query("SELECT * FROM $JOKE_TABLE_NAME ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomJoke(): MutableList<JokeDbObject>
}