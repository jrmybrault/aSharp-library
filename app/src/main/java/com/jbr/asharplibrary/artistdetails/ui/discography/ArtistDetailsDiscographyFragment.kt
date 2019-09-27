package com.jbr.asharplibrary.artistdetails.ui.discography

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jbr.asharplibrary.R
import com.jbr.asharplibrary.artistdetails.ui.ArtistDetailsViewModel
import com.jbr.asharplibrary.artistdetails.ui.discography.ArtistReleasesListAdapter.Companion.VIEW_TYPE_CATEGORY_INDEX
import com.jbr.asharplibrary.artistdetails.ui.discography.ArtistReleasesListAdapter.Companion.VIEW_TYPE_RELEASE_INFO_INDEX
import com.jbr.asharplibrary.shared.ui.ImageDownloader
import com.jbr.asharplibrary.sharedui.RecyclerViewDynamicSpanAdapter
import com.jbr.utils.isDeviceTablet
import kotlinx.android.synthetic.main.fragment_artist_details_discography.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ArtistDetailsDiscographyFragment : Fragment() {


    //region - Properties

    private val viewModel: ArtistDetailsViewModel by viewModel()

    private val imageDownloader: ImageDownloader by inject()

    private val artistReleasesListAdapter: ArtistReleasesListAdapter by lazyOf(ArtistReleasesListAdapter(imageDownloader))

    //endregion

    //region - Functions

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.displayableArtistReleases.observe(viewLifecycleOwner, Observer {
            artistReleasesListAdapter.submitList(it)
        })

        return inflater.inflate(R.layout.fragment_artist_details_discography, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        releasesRecyclerView.apply {
            adapter = artistReleasesListAdapter

            layoutManager = if (isDeviceTablet(resources)) {
                val itemWidth = resources.getDimensionPixelSize(R.dimen.release_item_width)
                RecyclerViewDynamicSpanAdapter.adapt(releasesRecyclerView, itemWidth)

                createTabletLayoutManage(artistReleasesListAdapter)
            } else {
                LinearLayoutManager(context) // FIXME: Use Gid layout with span at 1 and without size lookup adaptation)
            }
        }
    }

    private fun createTabletLayoutManage(adapter: ArtistReleasesListAdapter): RecyclerView.LayoutManager {
        val gridLayoutManager = GridLayoutManager(activity, 1) // Default span count that will be dynamically updated
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

            override fun getSpanSize(position: Int): Int = when (adapter.getItemViewType(position)) {
                VIEW_TYPE_CATEGORY_INDEX -> gridLayoutManager.spanCount
                VIEW_TYPE_RELEASE_INFO_INDEX -> 1
                else -> 1
            }
        }

        return gridLayoutManager
    }

    //endregion
}