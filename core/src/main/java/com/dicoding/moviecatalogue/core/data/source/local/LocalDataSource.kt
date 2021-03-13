package com.dicoding.moviecatalogue.core.data.source.local

import com.dicoding.moviecatalogue.core.data.source.local.entity.MovieTvShowEntity
import com.dicoding.moviecatalogue.core.data.source.local.room.MovieTvShowDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieTvShowDao: MovieTvShowDao) {
    fun getMovies(): Flow<List<MovieTvShowEntity>> = movieTvShowDao.getMovies()

    fun getTvShow(): Flow<List<MovieTvShowEntity>> = movieTvShowDao.getTvShow()

    fun getFavorite(isMovie: Boolean): Flow<List<MovieTvShowEntity>> = movieTvShowDao.getFavorite(isMovie)

    fun getDetailMovieTvShow(id: Int, isMovie: Boolean) = movieTvShowDao.getDetailMovieTvShow(id, isMovie)

    suspend fun insertMovieTvShow(data: List<MovieTvShowEntity>) = movieTvShowDao.insertMovieTvShow(data)

    fun updateMovieTvShow(data: MovieTvShowEntity) = movieTvShowDao.updateMovieTvShow(data)

    fun setFavoritedMovieTvShow(data: MovieTvShowEntity, newState: Boolean) {
        data.favorited = newState
        movieTvShowDao.updateMovieTvShow(data)
    }
}