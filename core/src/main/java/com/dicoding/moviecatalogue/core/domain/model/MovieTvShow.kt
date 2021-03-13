package com.dicoding.moviecatalogue.core.domain.model

data class MovieTvShow(
    val id: Int,
    val title: String,
    val genres: String,
    val releaseDate: String,
    val overview: String,
    val posterPath: String,
    val isMovie: Boolean,
    val favorited: Boolean
)
