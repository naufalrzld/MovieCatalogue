package com.dicoding.moviecatalogue.core.domain.usecase

import com.dicoding.moviecatalogue.core.domain.model.MovieTvShow
import com.dicoding.moviecatalogue.core.domain.repository.IMovieTvShowRepository

class MovieTvShowIntercator(private val repository: IMovieTvShowRepository) : MovieTvShowUseCase {
    override fun getAllMovie() = repository.getAllMovie()

    override fun getAllTVShow() = repository.getAllTVShow()

    override fun getDetailMovie(id: Int) = repository.getDetailMovie(id)

    override fun getDetailTvShow(id: Int) = repository.getDetailTvShow(id)

    override fun getFavorite(isMovie: Boolean) = repository.getFavorite(isMovie)

    override fun setFavoritedMovieTvShow(data: MovieTvShow, state: Boolean) = repository.setFavoritedMovieTvShow(data, state)
}