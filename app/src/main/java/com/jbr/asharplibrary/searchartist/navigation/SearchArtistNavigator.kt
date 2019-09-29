package com.jbr.asharplibrary.searchartist.navigation

import com.jbr.coredomain.ArtistIdentifier

interface SearchArtistNavigator {

    fun goToArtistDetails(identifier: ArtistIdentifier)
}