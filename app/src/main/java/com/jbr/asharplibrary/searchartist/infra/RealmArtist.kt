package com.jbr.asharplibrary.searchartist.infra

import com.jbr.coredomain.ArtistIdentifier
import com.jbr.coredomain.searchartist.Artist
import com.jbr.coredomain.searchartist.ArtistType
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmArtist : RealmObject() {

    //region - Properties

    @PrimaryKey
    var identifier: ArtistIdentifier = ""

    private var name: String = ""
    private var sortName: String = ""

    private var type: String = ""
    private var disambiguation: String? = null

    private var score: Int = 0

    //endregion

    //region - Functions

    fun asDomain(): Artist {
        return Artist(
            identifier = identifier,
            name = name,
            type = ArtistType.valueOf(type),
            disambiguation = disambiguation,
            sortName = sortName,
            score = score
        )
    }

    //endregion

    companion object {

        fun from(artist: Artist): RealmArtist {
            val realmArtist = RealmArtist()

            realmArtist.identifier = artist.identifier
            realmArtist.name = artist.name
            realmArtist.sortName = artist.sortName
            realmArtist.type = artist.type.toString()
            realmArtist.disambiguation = artist.disambiguation
            realmArtist.score = artist.score

            return realmArtist
        }
    }
}
