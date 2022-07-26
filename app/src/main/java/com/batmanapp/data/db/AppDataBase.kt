package com.batmanapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.batmanapp.data.db.entities.MovieDetailsEntity
import com.batmanapp.data.db.entities.MovieEntity

@Database(entities = [MovieEntity::class, MovieDetailsEntity::class], version = 2)

abstract class AppDataBase : RoomDatabase() {

    abstract fun dao(): AppDao?

}