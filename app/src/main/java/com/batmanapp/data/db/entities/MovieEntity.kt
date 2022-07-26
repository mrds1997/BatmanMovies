package com.batmanapp.data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "movieEntity", indices = [Index(value = ["imdbId"], unique = true)])
data class MovieEntity(
    @PrimaryKey
    val id: Int? = null,
    val imdbId: String?,
    val title: String,
    val poster: String,
    val year: String
)