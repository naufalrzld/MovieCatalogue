package com.dicoding.moviecatalogue

import android.app.Application
import com.dicoding.moviecatalogue.core.di.coreModule
import com.dicoding.moviecatalogue.di.appModule
import com.dicoding.moviecatalogue.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MovieCatalogueApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@MovieCatalogueApp)
            androidFileProperties()
            modules(coreModule, appModule, viewModelModule)
        }
    }
}