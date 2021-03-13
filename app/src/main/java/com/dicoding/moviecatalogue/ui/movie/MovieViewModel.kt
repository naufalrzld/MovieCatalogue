package com.dicoding.moviecatalogue.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.moviecatalogue.core.domain.usecase.MovieTvShowUseCase

class MovieViewModel(useCase: MovieTvShowUseCase) : ViewModel() {
    val movies = useCase.getAllMovie().asLiveData()
}