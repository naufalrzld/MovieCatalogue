package com.dicoding.moviecatalogue.core.di

import android.content.Context
import androidx.room.Room
import com.dicoding.moviecatalogue.core.BuildConfig
import com.dicoding.moviecatalogue.core.data.MovieRepository
import com.dicoding.moviecatalogue.core.data.source.local.LocalDataSource
import com.dicoding.moviecatalogue.core.data.source.local.room.MovieDatabase
import com.dicoding.moviecatalogue.core.data.source.local.room.MovieTvShowDao
import com.dicoding.moviecatalogue.core.data.source.remote.network.ApiServices
import com.dicoding.moviecatalogue.core.data.source.remote.RemoteDataSource
import com.dicoding.moviecatalogue.core.domain.repository.IMovieTvShowRepository
import com.dicoding.moviecatalogue.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val coreModule = module {
    single { provideApiService() }
    single { provideDatabase(androidContext()) }
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieTvShowRepository> { MovieRepository(get(), get(), get()) }
}

fun provideDatabase(context: Context): MovieTvShowDao {
    val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
    val factory = SupportFactory(passphrase)
    return Room.databaseBuilder(context, MovieDatabase::class.java, "moviedb.db")
        .openHelperFactory(factory)
        .build()
        .movieTvShowDao()
}

fun provideApiService(): ApiServices {
    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val hostName = "api.themoviedb.org"
    val certificatePinner = CertificatePinner.Builder()
        .add(hostName, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
        .add(hostName, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
        .add(hostName, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
        .add(hostName, "sha256/KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=")
        .build()

    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .certificatePinner(certificatePinner)
        .build()

    val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    return retrofit.create(ApiServices::class.java)
}