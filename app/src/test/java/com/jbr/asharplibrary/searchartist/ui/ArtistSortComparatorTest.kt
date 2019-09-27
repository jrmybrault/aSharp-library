package com.jbr.asharplibrary.searchartist.ui

import com.jbr.asharplibrary.utils.RandomArtistGenerator
import org.junit.Assert.assertTrue
import org.junit.Test

class ArtistSortComparatorTest {

    private val comparator = ArtistSortComparator()

    @Test
    fun `higher score precedes`() {
        // Arrange
        val artist1 = RandomArtistGenerator.generate(score = 80)
        val artist2 = RandomArtistGenerator.generate(score = 89)

        // Assert
        assertTrue(comparator.compare(artist1, artist2) > 0)
        assertTrue(comparator.compare(artist2, artist1) < 0)
    }

    @Test
    fun `when score is equal, sort name take precedence`() {
        // Arrange
        val artist1 = RandomArtistGenerator.generate(sortName = "Pink Floyd", score = 80)
        val artist2 = RandomArtistGenerator.generate(sortName = "Ennio Morricone", score = 80)

        // Assert
        assertTrue(comparator.compare(artist1, artist2) > 0)
        assertTrue(comparator.compare(artist2, artist1) < 0)
    }
}