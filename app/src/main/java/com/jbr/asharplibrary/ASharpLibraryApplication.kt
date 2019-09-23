package com.jbr.asharplibrary

import android.app.Application
import com.jbr.asharplibrary.di.networkingModule
import com.jbr.asharplibrary.searchartist.di.searchArtistModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ASharpLibraryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@ASharpLibraryApplication)

            modules(
                listOf(
                    networkingModule,
                    searchArtistModule
                )
            )
        }
    }
}
