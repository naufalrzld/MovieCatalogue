package com.dicoding.moviecatalogue.core.data.source.remote.response

import com.dicoding.moviecatalogue.core.data.GenreData
import com.google.gson.annotations.SerializedName

data class ResponseTVShow(
    val id: Int,
    val name: String,
    val genres: List<GenreData>?,
    val overview: String,

    @SerializedName("first_air_date")
    val releaseDate: String?,

    @SerializedName("poster_path")
    val posterPath: String?,
)
