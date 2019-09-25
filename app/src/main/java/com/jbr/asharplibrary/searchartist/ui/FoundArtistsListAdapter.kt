package com.jbr.asharplibrary.searchartist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.util.Consumer
import androidx.recyclerview.widget.ListAdapter
import com.jbr.asharplibrary.R

class FoundArtistsListAdapter : ListAdapter<DisplayableFoundArtistItem, FoundArtistViewHolder>(DisplayableFoundArtistItemDiffCallback()) {

    //region - Properties

    var onArtistTap: Consumer<Int>? = null
    var onReachingListEnd: Runnable? = null

    //region - Functions

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoundArtistViewHolder {
        return FoundArtistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_found_artist, parent, false))
    }

    override fun onBindViewHolder(holder: FoundArtistViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
        holder.itemView.setOnClickListener {
            onArtistTap?.accept(position)
        }

        if (position >= itemCount - NUMBER_OF_ITEMS_BEFORE_END) {
            onReachingListEnd?.run()
        }
    }

    //endregion

    companion object {
        private const val NUMBER_OF_ITEMS_BEFORE_END = 3
    }
}