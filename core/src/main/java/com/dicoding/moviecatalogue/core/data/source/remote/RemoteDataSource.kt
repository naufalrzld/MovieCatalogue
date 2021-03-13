package com.dicoding.moviecatalogue.core.data.source.remote

import com.dicoding.moviecatalogue.core.BuildConfig
import com.dicoding.moviecatalogue.core.data.source.remote.network.ApiResponse
import com.dicoding.moviecatalogue.core.data.source.remote.network.ApiServices
import com.dicoding.moviecatalogue.core.data.source.remote.response.ResponseMovie
import com.dicoding.moviecatalogue.core.data.source.remote.response.ResponseTVShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class RemoteDataSource(private val apiServices: ApiServices) {

    suspend fun getAllMovie() : Flow<ApiResponse<List<ResponseMovie>>> =
        flow {
            try {
                val response = apiServices.getNowPlayingMovie(BuildConfig.API_KEY)
                val movies = response.results
                if (movies.isNotEmpty()) {
                    emit(ApiResponse.Success(movies))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Timber.e("RemoteDataSource: $e")
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getTvShow() : Flow<ApiResponse<List<ResponseTVShow>>> =
        flow {
            try {
                val response = apiServices.getPopularTVShow(BuildConfig.API_KEY)
                val tvShow = response.results
                if (tvShow.isNotEmpty()) {
                    emit(ApiResponse.Success(tvShow))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Timber.e("RemoteDataSource: $e")
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetailMovie(id: Int) : Flow<ApiResponse<ResponseMovie>> =
        flow {
            try {
                val response = apiServices.getDetailMovie(id, BuildConfig.API_KEY)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Timber.e("RemoteDataSource: $e")
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getDetailTvShow(id: Int) : Flow<ApiResponse<ResponseTVShow>> =
        flow {
            try {
                val response = apiServices.getDetailTvShow(id, BuildConfig.API_KEY)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Timber.e("RemoteDataSource: $e")
            }
        }.flowOn(Dispatchers.IO)
}