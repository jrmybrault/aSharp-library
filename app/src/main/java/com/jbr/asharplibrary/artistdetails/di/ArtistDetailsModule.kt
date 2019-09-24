package com.jbr.asharplibrary.artistdetails.di

import com.jbr.asharplibrary.artistdetails.ui.ArtistDetailsViewModel
import com.jbr.asharplibrary.artistdetails.usecase.ArtistDetailsLoader
import com.jbr.asharplibrary.artistdetails.usecase.FakeArtistDetailsLoader
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val artistDetailsModule = module {

    viewModel { ArtistDetailsViewModel(get(), get()) }

    single<ArtistDetailsLoader> { FakeArtistDetailsLoader() }
}