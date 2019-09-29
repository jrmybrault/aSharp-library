package com.jbr.asharplibrary.searchartist.navigation

import com.jbr.coredomain.ArtistIdentifier

internal interface SearchArtistNavigator {

    fun goToArtistDetails(identifier: ArtistIdentifier)
}