package com.batmanapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.batmanapp.data.db.entities.MovieDetailsEntity
import com.batmanapp.data.db.entities.MovieEntity


@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieEntity: MovieEntity) : Long

    @Query("SELECT * FROM movieEntity")
    fun getMovies(): LiveData<List<MovieEntity>>

    @Delete
    fun deleteMovie(movieEntity: MovieEntity)

    @Query("SELECT COUNT(*) FROM movieEntity WHERE imdbId = :imdbId")
    fun getMovieIdCount(imdbId: String): Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetails(movieDetailsEntity: MovieDetailsEntity) : Long

    @Query("SELECT * FROM movieDetailsEntity WHERE imdbId = :imdbId")
    fun getMovieDetails(imdbId: String): LiveData<MovieDetailsEntity>

    @Query("SELECT COUNT(*) FROM movieDetailsEntity WHERE imdbId = :imdbId")
    fun getMovieDetailsIdCount(imdbId: String): Int

}