package com.batmanapp.models.response

import com.batmanapp.models.Rating
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    @SerializedName("Actors")
    @Expose
    val actors: String,

    @SerializedName("Awards")
    @Expose
    val awards: String,

    @SerializedName("BoxOffice")
    @Expose
    val boxOffice: String,

    @SerializedName("Country")
    @Expose
    val country: String,

    @SerializedName("DVD")
    @Expose
    val dvd: String,

    @SerializedName("Director")
    @Expose
    val director: String,

    @SerializedName("Genre")
    @Expose
    val genre: String,

    @SerializedName("Language")
    @Expose
    val language: String,

    @SerializedName("Metascore")
    @Expose
    val metaScore: String,

    @SerializedName("Plot")
    @Expose
    val plot: String,

    @SerializedName("Poster")
    @Expose
    val poster: String,

    @SerializedName("Production")
    @Expose
    val production: String,

    @SerializedName("Rated")
    @Expose
    val rated: String,

    @SerializedName("Ratings")
    @Expose
    val ratings: ArrayList<Rating>,

    @SerializedName("Released")
    @Expose
    val released: String,

    @SerializedName("Response")
    @Expose
    val response: String,

    @SerializedName("Runtime")
    @Expose
    val runTime: String,

    @SerializedName("Title")
    @Expose
    val title: String,

    @SerializedName("Type")
    @Expose
    val type: String,

    @SerializedName("Website")
    @Expose
    val website: String,

    @SerializedName("Writer")
    @Expose
    val writer: String,

    @SerializedName("Year")
    @Expose
    val year: String,

    @SerializedName("imdbID")
    @Expose
    val imdbId: String,

    @SerializedName("imdbRating")
    @Expose
    val imdbRating: String,

    @SerializedName("imdbVotes")
    @Expose
    val imdbVotes: String
)