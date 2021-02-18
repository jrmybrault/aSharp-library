package com.jbr.asharplibrary.searchartist.ui

import com.jbr.asharplibrary.random.RandomArtistGenerator
import com.jbr.asharplibrary.random.RandomStringGenerator
import com.jbr.asharplibrary.random.mockResources
import junit.framework.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DisplayableFoundArtistItemDiffCallbackTest {

    @Test
    fun `items with equal identifiers are the same`() {
        // Arrange
        val identifier = RandomStringGenerator.generate()

        val displayable1 = DisplayableFoundArtistItem(RandomArtistGenerator.generate(identifier = identifier), mockResources)
        val displayable2 = DisplayableFoundArtistItem(RandomArtistGenerator.generate(identifier = identifier), mockResources)

        // Act
        val itemsAreTheSame = DisplayableFoundArtistItemDiffCallback().areItemsTheSame(displayable1, displayable2)

        // Assert
        assertTrue(itemsAreTheSame)
    }

    @Test
    fun `items with different identifiers are not the same`() {
        // Arrange
        val identifier1 = RandomStringGenerator.generate()
        val displayable1 = DisplayableFoundArtistItem(RandomArtistGenerator.generate(identifier = identifier1), mockResources)

        val identifier2 = RandomStringGenerator.generate()
        val displayable2 = DisplayableFoundArtistItem(RandomArtistGenerator.generate(identifier = identifier2), mockResources)

        // Act
        val itemsAreTheSame = DisplayableFoundArtistItemDiffCallback().areItemsTheSame(displayable1, displayable2)

        // Assert
        assertFalse(itemsAreTheSame)
    }

    @Test
    fun `equal items have the same content`() {
        // Arrange
        val artist = RandomArtistGenerator.generate()

        val displayable1 = DisplayableFoundArtistItem(artist, mockResources)
        val displayable2 = DisplayableFoundArtistItem(artist, mockResources)

        // Act
        val areContentsTheSame = DisplayableFoundArtistItemDiffCallback().areContentsTheSame(displayable1, displayable2)

        // Assert
        assertTrue(areContentsTheSame)
    }

    @Test
    fun `different items have not the same content`() {
        // Arrange
        val displayable1 = DisplayableFoundArtistItem(RandomArtistGenerator.generate(), mockResources)
        val displayable2 = DisplayableFoundArtistItem(RandomArtistGenerator.generate(), mockResources)

        // Act
        val areContentsTheSame = DisplayableFoundArtistItemDiffCallback().areContentsTheSame(displayable1, displayable2)

        // Assert
        assertFalse(areContentsTheSame)
    }
}