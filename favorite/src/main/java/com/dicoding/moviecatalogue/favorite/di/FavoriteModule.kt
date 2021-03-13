package com.dicoding.moviecatalogue.favorite.di

import com.dicoding.moviecatalogue.favorite.ui.movie.MovieFavoriteViewModel
import com.dicoding.moviecatalogue.favorite.ui.tvshow.TvShowFavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { MovieFavoriteViewModel(get()) }
    viewModel { TvShowFavoriteViewModel(get()) }
}