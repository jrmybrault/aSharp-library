package com.jbr.asharplibrary.searchartist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.util.Consumer
import androidx.recyclerview.widget.RecyclerView
import com.jbr.asharplibrary.R
import kotlinx.android.synthetic.main.item_found_artist.view.*

class FoundArtistsListAdapter : RecyclerView.Adapter<FoundArtistsListAdapter.ViewHolder>() {

    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        //region - Properties

        private val nameTextView: TextView = rootView.nameTextView
        private val typeTextView: TextView = rootView.typeTextView

        //region - Functions

        fun bind(item: DisplayableFoundArtistItem) {
            nameTextView.text = item.name
            typeTextView.text = itemView.context.getString(item.typeStringId)
        }
    }

    //region - Properties

    var artists: List<DisplayableFoundArtistItem> = emptyList()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    var onArtistSelected: Consumer<Int>? = null

    //region - Functions

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_found_artist, parent, false))
    }

    override fun getItemCount() = artists.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(artists[position])
        holder.itemView.setOnClickListener {
            onArtistSelected?.accept(position)
        }
    }
}