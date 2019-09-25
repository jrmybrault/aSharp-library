package com.jbr.asharplibrary.searchartist.di

import com.jbr.asharplibrary.searchartist.infra.MBArtistFinder
import com.jbr.asharplibrary.searchartist.ui.SearchArtistViewModel
import com.jbr.asharplibrary.searchartist.usecase.ArtistFinder
import com.jbr.asharplibrary.searchartist.usecase.PreviousArtistSearchesLocalRepository
import com.jbr.asharplibrary.searchartist.usecase.PreviousArtistSearchesRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchArtistModule = module {

    viewModel { SearchArtistViewModel(get(), get(), get()) }

    single<ArtistFinder> { MBArtistFinder(get()) }

    single<PreviousArtistSearchesRepository> { PreviousArtistSearchesLocalRepository(get()) }
}