package com.jbr.asharplibrary.searchartist.usecase

import com.jbr.asharplibrary.shareddomain.ArtistIdentifier

interface SearchArtistNavigator {

    fun goToArtistDetails(identifier: ArtistIdentifier)
}