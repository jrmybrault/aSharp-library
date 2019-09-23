package com.jbr.asharplibrary.searchartist.di

import com.jbr.asharplibrary.searchartist.ui.SearchArtistViewModel
import com.jbr.asharplibrary.searchartist.usecase.FakeArtistFinder
import com.jbr.asharplibrary.searchartist.usecase.IArtistFinder
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchArtistModule = module {

    viewModel { SearchArtistViewModel(get(), get()) }

    single<IArtistFinder> { FakeArtistFinder() }
}