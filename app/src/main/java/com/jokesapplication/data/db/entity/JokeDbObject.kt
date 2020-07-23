package com.jokesapplication.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val JOKE_TABLE_NAME = "jokes"

@Entity(tableName = JOKE_TABLE_NAME)
data class JokeDbObject(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    @ColumnInfo(name = "joke_id")
    val jokeId: Int?,

    @ColumnInfo(name = "text")
    val text: String,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long

)