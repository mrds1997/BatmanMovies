package com.batmanapp.models.response

import com.batmanapp.models.Movie
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetBatmanMovies(
    @SerializedName("Search")
    @Expose
    val movies: ArrayList<Movie>?
)