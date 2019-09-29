package com.jbr.asharplibrary.searchartist.ui

import androidx.recyclerview.widget.DiffUtil

class DisplayableFoundArtistItemDiffCallback : DiffUtil.ItemCallback<DisplayableFoundArtistItem>() {

    override fun areItemsTheSame(oldItem: DisplayableFoundArtistItem, newItem: DisplayableFoundArtistItem): Boolean {
        return oldItem.identifier == newItem.identifier
    }

    override fun areContentsTheSame(oldItem: DisplayableFoundArtistItem, newItem: DisplayableFoundArtistItem): Boolean {
        return oldItem == newItem
    }
}
