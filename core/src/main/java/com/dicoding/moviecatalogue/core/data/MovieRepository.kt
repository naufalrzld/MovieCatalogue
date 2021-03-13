package com.dicoding.moviecatalogue.core.data

import com.dicoding.moviecatalogue.core.data.source.local.LocalDataSource
import com.dicoding.moviecatalogue.core.data.source.remote.RemoteDataSource
import com.dicoding.moviecatalogue.core.data.source.remote.network.ApiResponse
import com.dicoding.moviecatalogue.core.data.source.remote.response.ResponseMovie
import com.dicoding.moviecatalogue.core.data.source.remote.response.ResponseTVShow
import com.dicoding.moviecatalogue.core.domain.model.MovieTvShow
import com.dicoding.moviecatalogue.core.domain.repository.IMovieTvShowRepository
import com.dicoding.moviecatalogue.core.utils.AppExecutors
import com.dicoding.moviecatalogue.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieTvShowRepository {

    override fun getAllMovie(): Flow<Resource<List<MovieTvShow>>> {
        return object : NetworkBoundResource<List<MovieTvShow>, List<ResponseMovie>>() {
            override fun loadFromDB(): Flow<List<MovieTvShow>> {
                return localDataSource.getMovies().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MovieTvShow>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResponseMovie>>> {
                return remoteDataSource.getAllMovie()
            }

            override suspend fun saveCallResult(data: List<ResponseMovie>) {
                val movies = DataMapper.mapMovieResponseToEntity(data)
                localDataSource.insertMovieTvShow(movies)
            }

        }.asFlow()
    }

    override fun getAllTVShow(): Flow<Resource<List<MovieTvShow>>> {
        return object : NetworkBoundResource<List<MovieTvShow>, List<ResponseTVShow>>() {
            override fun loadFromDB(): Flow<List<MovieTvShow>> {
                return localDataSource.getTvShow().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MovieTvShow>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResponseTVShow>>> {
                return remoteDataSource.getTvShow()
            }

            override suspend fun saveCallResult(data: List<ResponseTVShow>) {
                val tvShowList = DataMapper.mapTvShowResponseToEntity(data)
                localDataSource.insertMovieTvShow(tvShowList)
            }

        }.asFlow()
    }

    override fun getDetailMovie(id: Int): Flow<Resource<MovieTvShow>> {
        return object : NetworkBoundResource<MovieTvShow, ResponseMovie>() {
            override fun loadFromDB(): Flow<MovieTvShow> =
                localDataSource.getDetailMovieTvShow(id, true).map {
                    Timber.d("NAUFAL -> loadFromDB -> $it")
                    DataMapper.mapEntityToDomain(it)
                }

            override fun shouldFetch(data: MovieTvShow?): Boolean {
                Timber.d("NAUFAL -> shouldFetch -> $data")
                return data?.genres.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<ResponseMovie>> {
                Timber.d("NAUFAL -> createCall")
                return remoteDataSource.getDetailMovie(id)
            }

            override suspend fun saveCallResult(data: ResponseMovie) {
                Timber.d("NAUFAL -> saveCallResult -> $data")
                val movie = DataMapper.mapMovieResponseToEntity(data)
                appExecutors.diskIO().execute { localDataSource.updateMovieTvShow(movie) }
            }

        }.asFlow()
    }

    override fun getDetailTvShow(id: Int): Flow<Resource<MovieTvShow>> {
        return object : NetworkBoundResource<MovieTvShow, ResponseTVShow>() {
            override fun loadFromDB(): Flow<MovieTvShow> =
                localDataSource.getDetailMovieTvShow(id, false).map {
                    Timber.d("NAUFAL -> loadFromDB -> $it")
                    DataMapper.mapEntityToDomain(it)
                }

            override fun shouldFetch(data: MovieTvShow?): Boolean {
                Timber.d("NAUFAL -> shouldFetch -> $data")
                return data?.genres.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<ResponseTVShow>> {
                Timber.d("NAUFAL -> createCall")
                return remoteDataSource.getDetailTvShow(id)
            }

            override suspend fun saveCallResult(data: ResponseTVShow) {
                Timber.d("NAUFAL -> saveCallResult -> $data")
                val tvShow = DataMapper.mapTvShowResponseToEntity(data)
                appExecutors.diskIO().execute { localDataSource.updateMovieTvShow(tvShow) }
            }

        }.asFlow()
    }

    override fun getFavorite(isMovie: Boolean): Flow<List<MovieTvShow>> =
        localDataSource.getFavorite(isMovie).map {
            DataMapper.mapEntitiesToDomain(it)
        }

    override fun setFavoritedMovieTvShow(data: MovieTvShow, state: Boolean) {
        val movieTvShowEntity = DataMapper.mapDomainToEntity(data)
        appExecutors.diskIO().execute { localDataSource.setFavoritedMovieTvShow(movieTvShowEntity, state) }
    }
}