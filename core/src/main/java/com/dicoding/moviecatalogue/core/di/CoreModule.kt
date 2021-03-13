package com.dicoding.moviecatalogue.core.di

import android.content.Context
import androidx.room.Room
import com.dicoding.moviecatalogue.core.BuildConfig
import com.dicoding.moviecatalogue.core.data.MovieRepository
import com.dicoding.moviecatalogue.core.data.source.local.LocalDataSource
import com.dicoding.moviecatalogue.core.data.source.local.room.MovieDatabase
import com.dicoding.moviecatalogue.core.data.source.remote.network.ApiServices
import com.dicoding.moviecatalogue.core.data.source.remote.RemoteDataSource
import com.dicoding.moviecatalogue.core.domain.repository.IMovieTvShowRepository
import com.dicoding.moviecatalogue.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val coreModule = module {
    single { provideApiService() }
    single { provideDatabase(androidContext()) }
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieTvShowRepository> { MovieRepository(get(), get(), get()) }
}

fun provideDatabase(context: Context) =
    Room.databaseBuilder(context, MovieDatabase::class.java, "moviedb")
        .build()
        .academyDao()

fun provideApiService(): ApiServices {
    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    return retrofit.create(ApiServices::class.java)
}