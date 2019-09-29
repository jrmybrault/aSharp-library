package com.jbr.asharplibrary.searchartist.ui

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_found_artist.view.*

// FIXME: Use Dexopener to avoid making FoundArtistViewHolder final and move to FoundArtistViewHolderTest to instrumented test
open class FoundArtistViewHolder(
    rootView: View,
    private val nameTextView: TextView = rootView.itemFoundArtistNameTextView,
    private val typeTextView: TextView = rootView.itemFoundArtistTypeTextView,
    private val tagsTextView: TextView = rootView.itemFoundArtistTagsTextView,
    private val clearButton: ImageButton = rootView.itemFoundArtistClearButton
) : RecyclerView.ViewHolder(rootView) {

    //region - Functions

    fun bind(item: DisplayableFoundArtistItem, onClear: Runnable? = null) {
        nameTextView.text = item.name
        typeTextView.text = item.typeText
        tagsTextView.isVisible = item.shouldDisplayTags
        tagsTextView.text = item.tagsText

        if (onClear != null) {
            clearButton.isVisible = true
            clearButton.setOnClickListener { onClear.run() }
        } else {
            clearButton.isVisible = false
        }
    }

    //endregion
}
