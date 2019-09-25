package com.jbr.asharplibrary.searchartist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.util.Consumer
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jbr.asharplibrary.R
import kotlinx.android.synthetic.main.item_found_artist.view.*

class PreviousArtistSearchesListAdapter : RecyclerView.Adapter<PreviousArtistSearchesListAdapter.ViewHolder>() {

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        //region - Properties

        private val nameTextView: TextView = rootView.itemFoundArtistNameTextView
        private val typeTextView: TextView = rootView.itemFoundArtistTypeTextView
        private val clearButton: ImageButton = rootView.itemFoundArtistClearButton

        //region - Functions

        fun bind(item: DisplayableFoundArtistItem, onClear: Runnable) {
            nameTextView.text = item.name
            typeTextView.text = itemView.context.getString(item.typeStringId)

            clearButton.isVisible = true
            clearButton.setOnClickListener { onClear.run() }
        }
    }

    val swipeToDeleteCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            onSwipedPreviousSearch(viewHolder.adapterPosition)
        }
    }
    
    //region - Properties

    var artists: List<DisplayableFoundArtistItem> = emptyList()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    var onArtistTap: Consumer<Int>? = null
    var onClearArtistTap: Consumer<Int>? = null

    //region - Functions

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_found_artist, parent, false))
    }

    override fun getItemCount() = artists.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(artists[position], onClear = Runnable {
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
