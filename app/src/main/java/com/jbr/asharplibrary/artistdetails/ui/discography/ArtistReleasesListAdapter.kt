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
import kotlinx.android.synthetic.main.item_found_artist.view.*
import kotlinx.android.synthetic.main.item_release_info.view.*

class ArtistReleasesListAdapter(private val imageDownloader: ImageDownloader) :
    ListAdapter<DisplayableArtistReleaseItemType, ArtistReleasesListAdapter.ViewHolder>(DisplayableArtistReleaseItemTypeDiffCallback()) {

    //region - ViewHolder

    sealed class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        class CategoryViewHolder(rootView: View) : ViewHolder(rootView) {

            //region - Properties

            private val nameTextView: TextView = rootView.nameTextView

            //region - Functions

            fun bind(item: DisplayableReleaseCategoryItem) {
                nameTextView.text = itemView.resources.getString(item.categoryNameId)
            }
        }

        class ReleaseInfoViewHolder(rootView: View, private val imageDownloader: ImageDownloader) : ViewHolder(rootView) {

            //region - Properties

            private val frontCoverImageView: ImageView = rootView.frontCoverImageView
            private val titleTextView: TextView = rootView.titleTextView
            private val releaseYearTextView: TextView = rootView.releaseYearTextView

            //region - Functions

            fun bind(item: DisplayableReleaseInfoItem) {
                frontCoverImageView.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.cover_the_wall))
                titleTextView.text = item.title
                releaseYearTextView.text = item.releaseYearText

                imageDownloader.downloadImage(item.smallFrontCoverUri, frontCoverImageView, itemView.context)
            }
        }
    }

    //endregion

    //region - Functions

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DisplayableReleaseCategoryItem -> VIEW_TYPE_CATEGORY_INDEX
            is DisplayableReleaseInfoItem -> VIEW_TYPE_RELEASE_INFO_INDEX
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CATEGORY_INDEX -> ViewHolder.CategoryViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_release_category, parent, false)
            )
            VIEW_TYPE_RELEASE_INFO_INDEX -> ViewHolder.ReleaseInfoViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_release_info, parent, false),
                imageDownloader
            )
            else -> throw IllegalStateException("Could not create view holder for view type \"$viewType\".")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        if (holder is ViewHolder.CategoryViewHolder && item is DisplayableReleaseCategoryItem) {
            holder.bind(item)
        } else if (holder is ViewHolder.ReleaseInfoViewHolder && item is DisplayableReleaseInfoItem) {
            holder.bind(item)
        } else {
            throw IllegalStateException("Could not bind view holder $holder at position \"$position\".")
        }
    }

    //endregion

    companion object {

        private const val VIEW_TYPE_CATEGORY_INDEX = 0
        private const val VIEW_TYPE_RELEASE_INFO_INDEX = 1
    }
}
