package com.dicoding.moviecatalogue.core.data.source.remote.response

import com.dicoding.moviecatalogue.core.data.GenreData
import com.google.gson.annotations.SerializedName

data class ResponseMovie(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("genres")
    val genres: List<GenreData>?,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("poster_path")
    val posterPath: String?,
)
