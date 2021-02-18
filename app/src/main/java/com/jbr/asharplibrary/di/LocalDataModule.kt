package com.jbr.asharplibrary.di

import com.jbr.asharplibrary.searchartist.infra.RealmPreviousArtistSearchesDao
import com.jbr.coredomain.searchartist.PreviousArtistSearchesLocalProvider
import io.realm.Realm
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localDataModule = module {

    single<PreviousArtistSearchesLocalProvider> {
        Realm.init(androidApplication()) // TODO: Should be initialized in a more global scope

        RealmPreviousArtistSearchesDao()
    }
}