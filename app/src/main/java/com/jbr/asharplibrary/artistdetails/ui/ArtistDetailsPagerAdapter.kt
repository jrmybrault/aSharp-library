package com.jbr.asharplibrary.artistdetails.ui

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jbr.asharplibrary.R

class ArtistDetailsPagerAdapter(
    manager: FragmentManager,
    private val resources: Resources
) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    enum class PageType {

        ABOUT,
        DISCOGRAPHY
    }

    //region - Functions

    override fun getItem(position: Int): Fragment {
        return when (pageType(position)) {
            PageType.ABOUT -> ArtistDetailsAboutFragment()
            PageType.DISCOGRAPHY -> ArtistDetailsDiscographyFragment()
        }
    }

    private fun pageType(position: Int): PageType {
        return PageType.values()[position]
    }

    override fun getCount(): Int = PageType.values().size

    override fun getPageTitle(position: Int): CharSequence? {
        return resources.getString(pageType(position).displayTextId())
    }

    //endregion
}

fun ArtistDetailsPagerAdapter.PageType.displayTextId(): Int {
    return when (this) {
        ArtistDetailsPagerAdapter.PageType.ABOUT -> R.string.artist_details_about
        ArtistDetailsPagerAdapter.PageType.DISCOGRAPHY -> R.string.artist_details_discography
    }
}