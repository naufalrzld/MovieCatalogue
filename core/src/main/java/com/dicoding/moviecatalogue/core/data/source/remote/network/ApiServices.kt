package com.dicoding.moviecatalogue.core.data.source.remote.network

import com.dicoding.moviecatalogue.core.data.source.remote.response.BaseResponseApi
import com.dicoding.moviecatalogue.core.data.source.remote.response.ResponseMovie
import com.dicoding.moviecatalogue.core.data.source.remote.response.ResponseTVShow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovie(
        @Query("api_key") apiKey: String
    ): BaseResponseApi<ResponseMovie>

    @GET("tv/popular")
    suspend fun getPopularTVShow(
        @Query("api_key") apiKey: String
    ): BaseResponseApi<ResponseTVShow>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String
    ): ResponseMovie

    @GET("tv/{tv_id}")
    suspend fun getDetailTvShow(
        @Path("tv_id") tvID: Int,
        @Query("api_key") apiKey: String
    ): ResponseTVShow

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): BaseResponseApi<ResponseMovie>

    @GET("search/tv")
    suspend fun searchTvShow(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): BaseResponseApi<ResponseTVShow>
}