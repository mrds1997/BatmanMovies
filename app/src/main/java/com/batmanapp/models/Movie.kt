package com.batmanapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Poster")
    @Expose
    val poster: String?,

    @SerializedName("Title")
    @Expose
    val title: String?,

    @SerializedName("Type")
    @Expose
    val type: String?,

    @SerializedName("Year")
    @Expose
    val year: String?,

    @SerializedName("imdbID")
    @Expose
    val imdbId: String?
)