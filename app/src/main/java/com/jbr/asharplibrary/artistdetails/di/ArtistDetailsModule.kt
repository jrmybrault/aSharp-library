package com.jbr.asharplibrary.artistdetails.di

import com.jbr.asharplibrary.artistdetails.infra.MBArtistDetailsLoader
import com.jbr.asharplibrary.artistdetails.ui.ArtistDetailsViewModel
import com.jbr.asharplibrary.artistdetails.usecase.ArtistDetailsLoader
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val artistDetailsModule = module {

    viewModel { ArtistDetailsViewModel(get(), get()) }

    single<ArtistDetailsLoader> { MBArtistDetailsLoader(get()) }
}