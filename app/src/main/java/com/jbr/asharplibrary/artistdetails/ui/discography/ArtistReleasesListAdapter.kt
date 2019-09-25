package com.jbr.asharplibrary.artistdetails.ui.discography

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jbr.asharplibrary.R
import com.jbr.asharplibrary.shared.ui.ImageDownloader
import kotlinx.android.synthetic.main.item_artist_release.view.*

class ArtistReleasesListAdapter(private val imageDownloader: ImageDownloader) :
    ListAdapter<DisplayableArtistReleaseItem, ArtistReleasesListAdapter.ViewHolder>(DisplayableArtistReleaseItemDiffCallback()) {

    class ViewHolder(rootView: View, private val imageDownloader: ImageDownloader) : RecyclerView.ViewHolder(rootView) {

        //region - Properties

        private val frontCoverImageView: ImageView = rootView.frontCoverImageView
        private val titleTextView: TextView = rootView.titleTextView
        private val releaseYearTextView: TextView = rootView.releaseYearTextView

        //region - Functions

        fun bind(item: DisplayableArtistReleaseItem) {
            frontCoverImageView.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.cover_the_wall))
            titleTextView.text = item.title
            releaseYearTextView.text = item.releaseYearText

            imageDownloader.downloadImage(item.smallFrontCoverUri, frontCoverImageView, itemView.context)
        }
    }

    //region - Functions

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_artist_release, parent, false),
            imageDownloader
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}