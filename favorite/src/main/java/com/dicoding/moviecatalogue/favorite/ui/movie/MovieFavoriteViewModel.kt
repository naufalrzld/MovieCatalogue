package com.dicoding.moviecatalogue.favorite.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.moviecatalogue.core.domain.usecase.MovieTvShowUseCase

class MovieFavoriteViewModel(useCase: MovieTvShowUseCase) : ViewModel() {
    val movies = useCase.getFavorite(true).asLiveData()
}