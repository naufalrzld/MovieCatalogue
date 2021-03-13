package com.dicoding.moviecatalogue.ui.detail

import androidx.lifecycle.*
import com.dicoding.moviecatalogue.core.data.Resource
import com.dicoding.moviecatalogue.core.domain.model.MovieTvShow
import com.dicoding.moviecatalogue.core.domain.usecase.MovieTvShowUseCase
import timber.log.Timber

class DetailViewModel(private val useCase: MovieTvShowUseCase) : ViewModel() {
    private var movieTvID = MutableLiveData<Int>()
    private var isMovie = false

    fun isMovie(isMovie: Boolean) {
        this.isMovie = isMovie
    }

    fun setSelectedMovieTv(movieTvID: Int) {
        this.movieTvID.value = movieTvID
    }

    var movieTvShow: LiveData<Resource<MovieTvShow>> = Transformations.switchMap(movieTvID) { id ->
        Timber.d("NAUFAL -> $isMovie")
        if (isMovie) useCase.getDetailMovie(id).asLiveData() else useCase.getDetailTvShow(id).asLiveData()
    }

    fun setFavorite() {
        val moviewTvShow = movieTvShow.value
        if (moviewTvShow != null) {
            val movieTvShowEntity = moviewTvShow.data

            if (movieTvShowEntity != null) {
                val newState = !movieTvShowEntity.favorited
                useCase.setFavoritedMovieTvShow(movieTvShowEntity, newState)
            }
        }
    }
}