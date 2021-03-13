package com.dicoding.moviecatalogue.core.utils

import com.dicoding.moviecatalogue.core.data.source.local.entity.MovieTvShowEntity
import com.dicoding.moviecatalogue.core.data.source.remote.response.ResponseMovie
import com.dicoding.moviecatalogue.core.data.source.remote.response.ResponseTVShow
import com.dicoding.moviecatalogue.core.domain.model.MovieTvShow

object DataMapper {
    fun mapMovieResponseToEntity(input: List<ResponseMovie>): List<MovieTvShowEntity> {
        val movies = ArrayList<MovieTvShowEntity>()
        input.map {
            val movie = MovieTvShowEntity(
                id = it.id,
                title = it.title,
                genres = "",
                overview = it.overview,
                releaseDate = it.releaseDate ?: "-",
                posterPath = it.posterPath ?: "",
                isMovie = true,
                favorited = false
            )
            movies.add(movie)
        }

        return movies
    }

    fun mapTvShowResponseToEntity(input: List<ResponseTVShow>): List<MovieTvShowEntity> {
        val movies = ArrayList<MovieTvShowEntity>()
        input.map {
            val movie = MovieTvShowEntity(
                id = it.id,
                title = it.name,
                genres = "",
                overview = it.overview,
                releaseDate = it.releaseDate ?: "-",
                posterPath = it.posterPath ?: "",
                isMovie = false,
                favorited = false
            )
            movies.add(movie)
        }

        return movies
    }

    fun mapEntitiesToDomain(input: List<MovieTvShowEntity>): List<MovieTvShow> =
        input.map {
            MovieTvShow(
                id = it.id,
                title = it.title,
                genres = it.genres,
                overview = it.overview,
                releaseDate = it.releaseDate,
                posterPath = it.posterPath,
                isMovie = it.isMovie,
                favorited = it.favorited
            )
        }

    fun mapEntityToDomain(input: MovieTvShowEntity) = MovieTvShow(
        id = input.id,
        title = input.title,
        genres = input.genres,
        overview = input.overview,
        releaseDate = input.releaseDate,
        posterPath = input.posterPath,
        isMovie = input.isMovie,
        favorited = input.favorited
    )

    fun mapMovieResponseToEntity(input: ResponseMovie): MovieTvShowEntity {
        val genres = ArrayList<String>()
        input.genres?.forEach { genres.add(it.name) }
        val genre = genres.joinToString(" - ")

        return MovieTvShowEntity(
            id = input.id,
            title = input.title,
            genres = genre,
            overview = input.overview,
            releaseDate = input.releaseDate ?: "-",
            posterPath = input.posterPath ?: "",
            isMovie = true,
            favorited = false
        )
    }

    fun mapTvShowResponseToEntity(input: ResponseTVShow): MovieTvShowEntity {
        val genres = ArrayList<String>()
        input.genres?.forEach { genres.add(it.name) }
        val genre = genres.joinToString(" - ")

        return MovieTvShowEntity(
            id = input.id,
            title = input.name,
            genres = genre,
            overview = input.overview,
            releaseDate = input.releaseDate ?: "-",
            posterPath = input.posterPath ?: "",
            isMovie = false,
            favorited = false
        )
    }

    fun mapDomainToEntity(input: MovieTvShow) = MovieTvShowEntity(
        id = input.id,
        title = input.title,
        genres = input.genres,
        overview = input.overview,
        releaseDate = input.releaseDate,
        posterPath = input.posterPath,
        isMovie = input.isMovie,
        favorited = input.favorited
    )

}