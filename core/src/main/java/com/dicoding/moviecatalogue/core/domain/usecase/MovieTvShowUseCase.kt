package com.dicoding.moviecatalogue.core.domain.usecase

import com.dicoding.moviecatalogue.core.data.Resource
import com.dicoding.moviecatalogue.core.domain.model.MovieTvShow
import kotlinx.coroutines.flow.Flow

interface MovieTvShowUseCase {
    fun getAllMovie(): Flow<Resource<List<MovieTvShow>>>

    fun getAllTVShow(): Flow<Resource<List<MovieTvShow>>>

    fun getDetailMovie(id: Int): Flow<Resource<MovieTvShow>>

    fun getDetailTvShow(id: Int): Flow<Resource<MovieTvShow>>

    fun getFavorite(isMovie: Boolean): Flow<List<MovieTvShow>>

    fun setFavoritedMovieTvShow(data: MovieTvShow, state: Boolean)
}