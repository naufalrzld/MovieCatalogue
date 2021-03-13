package com.dicoding.moviecatalogue.favorite.ui.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.moviecatalogue.core.domain.usecase.MovieTvShowUseCase

class TvShowFavoriteViewModel(useCase: MovieTvShowUseCase) : ViewModel() {
    val tvShow = useCase.getFavorite(false).asLiveData()
}