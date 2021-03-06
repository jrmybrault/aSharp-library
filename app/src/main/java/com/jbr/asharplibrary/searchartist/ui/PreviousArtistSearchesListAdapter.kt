package com.jbr.asharplibrary.searchartist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.util.Consumer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jbr.asharplibrary.R

class PreviousArtistSearchesListAdapter : ListAdapter<DisplayableFoundArtistItem, FoundArtistViewHolder>(DisplayableFoundArtistItemDiffCallback()) {

    //region - Properties

    var onArtistTap: Consumer<Int>? = null
    var onClearArtistTap: Consumer<Int>? = null

    val swipeToDeleteCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            onSwipedPreviousSearch(viewHolder.adapterPosition)
        }
    }

    //region - Functions

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoundArtistViewHolder {
        return FoundArtistViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_found_artist, parent, false))
    }

    override fun onBindViewHolder(holder: FoundArtistViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, onClear = Runnable {
            onClearArtistTap?.accept(position)
        })
        holder.itemView.setOnClickListener {
            onArtistTap?.accept(position)
        }
    }


    private fun onSwipedPreviousSearch(position: Int) {
        onClearArtistTap?.accept(position)
    }
}
