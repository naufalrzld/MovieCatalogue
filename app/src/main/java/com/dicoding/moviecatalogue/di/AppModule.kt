package com.dicoding.moviecatalogue.di

import com.dicoding.moviecatalogue.core.domain.usecase.MovieTvShowIntercator
import com.dicoding.moviecatalogue.core.domain.usecase.MovieTvShowUseCase
import org.koin.dsl.module

val appModule = module {
    factory<MovieTvShowUseCase> { MovieTvShowIntercator(get()) }
}