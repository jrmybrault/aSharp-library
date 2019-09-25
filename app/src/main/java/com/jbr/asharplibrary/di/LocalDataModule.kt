package com.jbr.asharplibrary.di

import com.jbr.asharplibrary.searchartist.domain.PreviousArtistSearchesLocalProvider
import com.jbr.asharplibrary.searchartist.infra.RealmPreviousArtistSearchesDao
import io.realm.Realm
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localDataModule = module {

    single<PreviousArtistSearchesLocalProvider> {
        Realm.init(androidApplication()) // TODO: Shouldn't be there

        RealmPreviousArtistSearchesDao()
    }
}