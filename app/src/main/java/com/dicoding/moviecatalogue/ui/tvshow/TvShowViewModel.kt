package com.dicoding.moviecatalogue.ui.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.moviecatalogue.core.domain.usecase.MovieTvShowUseCase

class TvShowViewModel(useCase: MovieTvShowUseCase) : ViewModel() {
    val tvShow = useCase.getAllTVShow().asLiveData()
}