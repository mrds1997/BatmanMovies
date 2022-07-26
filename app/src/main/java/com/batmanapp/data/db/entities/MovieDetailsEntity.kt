package com.batmanapp.data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "movieDetailsEntity")
data class MovieDetailsEntity(
    @PrimaryKey
    val id: Int? = null,
    val imdbId: String?,
    val poster: String,
    val country: String,
    val year: String,
    val actors: String,
    val genre: String,
    val imdbRate: String
)