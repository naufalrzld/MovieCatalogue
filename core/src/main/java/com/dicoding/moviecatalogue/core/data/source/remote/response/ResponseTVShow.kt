package com.dicoding.moviecatalogue.core.data.source.remote.response

import com.dicoding.moviecatalogue.core.data.GenreData
import com.google.gson.annotations.SerializedName

data class ResponseTVShow(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("genres")
    val genres: List<GenreData>?,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("first_air_date")
    val releaseDate: String?,

    @SerializedName("poster_path")
    val posterPath: String?,
)
