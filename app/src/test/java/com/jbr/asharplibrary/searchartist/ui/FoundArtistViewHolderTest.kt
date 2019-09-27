package com.jbr.asharplibrary.searchartist.ui

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import com.jbr.asharplibrary.utils.RandomDisplayableFoundArtistItemGenerator
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.mockito.ArgumentCaptor
import kotlin.random.Random


class FoundArtistViewHolderTest {

    private val mockRootView = mock<View>()
    private val mockNameTextView = mock<TextView>()
    private val mockTypeTextView = mock<TextView>()
    private val mockTagsTextView = mock<TextView>()
    private val mockClearButton = mock<ImageButton>()

    private val viewHolder = FoundArtistViewHolder(
        mockRootView,
        mockNameTextView,
        mockTypeTextView,
        mockTagsTextView,
        mockClearButton
    )

    @Test
    fun `bind updates view widgets properties`() {
        // Arrange
        val displayable = RandomDisplayableFoundArtistItemGenerator.generate()

        // Act
        viewHolder.bind(displayable)

        // Assert
        verify(mockNameTextView).text = displayable.name
        verify(mockTypeTextView).text = displayable.typeText
        verify(mockTagsTextView).text = displayable.tagsText
        verify(mockTagsTextView).isVisible = displayable.shouldDisplayTags
    }

    @Test
    fun `clear button is only visible when binding with a clear callback`() {
        // Arrange
        val displayable = RandomDisplayableFoundArtistItemGenerator.generate()
        val optionalClearCallback = if (Random.nextBoolean()) {
            mock<Runnable>()
        } else {
            null
        }

        // Act
        viewHolder.bind(displayable, optionalClearCallback)

        // Assert
        verify(mockClearButton).isVisible = optionalClearCallback != null
    }

    @Test
    fun `after binding, clear callback is called when clear button is clicked`() {
        // Arrange
        val displayable = RandomDisplayableFoundArtistItemGenerator.generate()
        val clearCallback = mock<Runnable>()

        val captor = ArgumentCaptor.forClass(View.OnClickListener::class.java)

        // Act
        viewHolder.bind(displayable, clearCallback)

        // Assert
        verify(mockClearButton).setOnClickListener(captor.capture())

        captor.value.onClick(mockClearButton)
        verify(clearCallback).run()
    }
}