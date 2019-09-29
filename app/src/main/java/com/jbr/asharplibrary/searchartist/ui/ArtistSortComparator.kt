package com.jbr.asharplibrary.searchartist.ui

import com.jbr.coredomain.searchartist.Artist

class ArtistSortComparator : Comparator<Artist> {

    override fun compare(artist1: Artist, artist2: Artist): Int {
        val scoreDifference = artist2.score - artist1.score

        return if (scoreDifference != 0) {
            scoreDifference
        } else {
            artist1.sortName.compareTo(artist2.sortName)
        }
    }
}