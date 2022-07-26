package com.batmanapp.data.repositories

import com.batmanapp.data.db.AppDataBase
import com.batmanapp.data.db.entities.MovieDetailsEntity
import com.batmanapp.data.db.entities.MovieEntity
import com.batmanapp.data.remote.ApiService
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: ApiService,
    private val db: AppDataBase
): BaseRepository() {

    suspend fun getBatmanMoviesList(page: Int) = safeApiCall { api.getMoviesList(page = page) }

    suspend fun getBatmanMovieDetails(imdbId: String) = safeApiCall { api.getMovieDetails(imdbId = imdbId) }

    fun insertInMovieEntity(movieEntity: MovieEntity) = db.dao()?.insertMovie(movieEntity)

    fun getMoviesFromEntity() = db.dao()?.getMovies()

    fun getMovieIdCount(videoId: String) = db.dao()?.getMovieIdCount(videoId)

    suspend fun deleteVideo(movieEntity: MovieEntity) = db.dao()?.deleteMovie(movieEntity)

    fun insertInMovieDetailsEntity(movieDetailsEntity: MovieDetailsEntity) = db.dao()?.insertMovieDetails(movieDetailsEntity)

    fun getMovieDetailsFromEntity(imdbId: String) = db.dao()?.getMovieDetails(imdbId)

    fun getMovieDetailsIdCount(imdbId: String) = db.dao()?.getMovieDetailsIdCount(imdbId)


}