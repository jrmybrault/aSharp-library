package com.jbr.asharplibrary.searchartist.ui

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_found_artist.view.*

class FoundArtistViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

    //region - Properties

    private val nameTextView: TextView = rootView.itemFoundArtistNameTextView
    private val typeTextView: TextView = rootView.itemFoundArtistTypeTextView
    private val clearButton: ImageButton = rootView.itemFoundArtistClearButton

    //region - Functions

    fun bind(item: DisplayableFoundArtistItem, onClear: Runnable? = null) {
        nameTextView.text = item.name
        typeTextView.text = item.disambiguatedTypeText

        if (onClear != null) {
            clearButton.isVisible = true
            clearButton.setOnClickListener { onClear.run() }
        } else {
            clearButton.isVisible = false
        }
    }

    //endregion
}
