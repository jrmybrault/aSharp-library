package com.jbr.asharplibrary.searchartist.ui

import android.content.res.Resources
import com.jbr.asharplibrary.searchartist.domain.Artist
import com.jbr.asharplibrary.searchartist.domain.ArtistType
import com.jbr.asharplibrary.utils.RandomArtistGenerator
import com.jbr.asharplibrary.utils.RandomArtistTagGenerator
import com.jbr.asharplibrary.utils.RandomStringGenerator
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.*
import org.junit.Test

class DisplayableFoundArtistItemKtTest {

    private val mockResources = mock<Resources> {
        on { getString(any()) } doReturn RandomStringGenerator.generate()
    }

    @Test
    fun `type text equals type name when no disambiguation`() {
        // Arrange
        val mockTypeName = RandomStringGenerator.generate()

        val mockResources = mock<Resources> {
            on { getString(any()) } doReturn mockTypeName
        }

        val artist = RandomArtistGenerator.generate(type = ArtistType.SOLO, disambiguation = null)

        // Act
        val displayable = DisplayableFoundArtistItem(artist, mockResources)

        // Assert
        assertEquals(mockTypeName, displayable.typeText)
    }

    @Test
    fun `type text contains type name and disambiguation when present`() {
        // Arrange
        val mockTypeName = RandomStringGenerator.generate()

        val mockResources = mock<Resources> {
            on { getString(any()) } doReturn mockTypeName
        }

        val disambiguation = RandomStringGenerator.generate()

        val artist = RandomArtistGenerator.generate(type = ArtistType.SOLO, disambiguation = disambiguation)

        // Act
        val displayable = DisplayableFoundArtistItem(artist, mockResources)

        // Assert
        assertTrue(displayable.typeText.contains(mockTypeName))
        assertTrue(displayable.typeText.contains(disambiguation))
    }

    @Test
    fun `tags text contains all tags names, sorted by count, separated by spaces and with prefix`() {
        // Arrange
        val tag1Name = RandomStringGenerator.generate()
        val tag2Name = RandomStringGenerator.generate()
        val tag3Name = RandomStringGenerator.generate()

        val tag1 = Artist.Tag(tag1Name, 5)
        val tag2 = Artist.Tag(tag2Name, 3)
        val tag3 = Artist.Tag(tag3Name, 8)

        val artist = RandomArtistGenerator.generate(tags = listOf(tag1, tag2, tag3))

        val prefix = "#"
        val separator = " "

        // Act
        val displayable = DisplayableFoundArtistItem(artist, mockResources)

        // Assert
        assertEquals("$prefix$tag3Name$separator$prefix$tag1Name$separator$prefix$tag2Name", displayable.tagsText)
    }

    @Test
    fun `tags text return null if no tags`() {
        // Arrange
        val artist = RandomArtistGenerator.generate(tags = emptyList())

        // Act
        val displayable = DisplayableFoundArtistItem(artist, mockResources)

        // Assert
        assertNull(displayable.tagsText)
    }

    @Test
    fun `tags text does only contains the first X elements`() {
        // Arrange
        val tags = (1..10).map { RandomArtistTagGenerator.generate(count = 1) }
        val artist = RandomArtistGenerator.generate(tags = tags)

        // Act
        val displayable = DisplayableFoundArtistItem(artist, mockResources)

        // Assert
        assertTrue(displayable.tagsText!!.contains(tags[0].name))
        assertTrue(displayable.tagsText!!.contains(tags[4].name))
        assertFalse(displayable.tagsText!!.contains(tags[5].name))
        assertFalse(displayable.tagsText!!.contains(tags[8].name))
    }
}