package com.jbr.asharplibrary.artistdetails.ui.discography

import com.jbr.coredomain.artistdetails.Release

internal class ReleasePrimaryTypeComparator : Comparator<Release.PrimaryType> {

    override fun compare(type1: Release.PrimaryType, type2: Release.PrimaryType): Int {
        return type1.sortIndex() - type2.sortIndex()
    }
}

private fun Release.PrimaryType.sortIndex(): Int {
    return when (this) {
        Release.PrimaryType.ALBUM -> 0
        Release.PrimaryType.SINGLE -> 2
        Release.PrimaryType.EP -> 1
        Release.PrimaryType.OTHER -> 3
    }
}