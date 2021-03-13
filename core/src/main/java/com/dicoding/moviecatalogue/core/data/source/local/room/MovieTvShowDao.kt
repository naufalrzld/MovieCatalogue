package com.dicoding.moviecatalogue.core.data.source.local.room

import androidx.room.*
import com.dicoding.moviecatalogue.core.data.source.local.entity.MovieTvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieTvShowDao {
    @Query("SELECT * FROM movietvshowentities WHERE isMovie = 1")
    fun getMovies(): Flow<List<MovieTvShowEntity>>

    @Query("SELECT * FROM movietvshowentities WHERE isMovie = 0")
    fun getTvShow(): Flow<List<MovieTvShowEntity>>

    @Query("SELECT * FROM movietvshowentities WHERE isMovie = :isMovie AND favorited = 1")
    fun getFavorite(isMovie: Boolean): Flow<List<MovieTvShowEntity>>

    @Query("SELECT * FROM movietvshowentities WHERE id = :id AND isMovie = :isMovie")
    fun getDetailMovieTvShow(id: Int, isMovie: Boolean): Flow<MovieTvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieTvShow(data: List<MovieTvShowEntity>)

    @Update
    fun updateMovieTvShow(data: MovieTvShowEntity)
}