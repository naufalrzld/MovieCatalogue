package com.dicoding.moviecatalogue.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.moviecatalogue.core.data.source.local.entity.MovieTvShowEntity

@Database(entities = [MovieTvShowEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieTvShowDao(): MovieTvShowDao
}