package com.dicoding.moviecatalogue.di

import com.dicoding.moviecatalogue.ui.detail.DetailViewModel
import com.dicoding.moviecatalogue.ui.movie.MovieViewModel
import com.dicoding.moviecatalogue.ui.tvshow.TvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}