package com.batmanapp.data.remote
import com.batmanapp.models.response.GetBatmanMovies
import com.batmanapp.models.response.MovieDetailsResponse
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET(".")
    suspend fun getMoviesList(
        @Query("apikey") apikey: String = "3e974fca",
        @Query("s") s: String = "batman",
        @Query("page") page: Int
        ): GetBatmanMovies

    @GET(".")
    suspend fun getMovieDetails(
        @Query("apikey") apikey: String = "3e974fca",
        @Query("i") imdbId: String
    ):MovieDetailsResponse

}