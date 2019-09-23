package com.jbr.asharplibrary

import android.app.Application
import com.jbr.asharplibrary.searchartist.di.searchArtistModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ASharpLibraryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ASharpLibraryApplication)

            modules(listOf(searchArtistModule))
        }
    }
}
