package com.dicoding.moviecatalogue.core.data.source.remote.response

import com.dicoding.moviecatalogue.core.data.GenreData
import com.google.gson.annotations.SerializedName

data class ResponseMovie(
    val id: Int,
    val title: String,
    val genres: List<GenreData>?,
    val overview: String,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("poster_path")
    val posterPath: String?,
)
