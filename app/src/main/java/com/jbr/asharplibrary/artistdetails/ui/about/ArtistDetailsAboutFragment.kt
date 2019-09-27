package com.jbr.asharplibrary.artistdetails.ui.about

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jbr.asharplibrary.R
import com.jbr.asharplibrary.artistdetails.ui.ArtistDetailsViewModel
import com.jbr.asharplibrary.artistdetails.usecase.ArtistDetailsNavigator
import kotlinx.android.synthetic.main.fragment_artist_details_about.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.ref.WeakReference

class ArtistDetailsAboutFragment : Fragment(), ArtistDetailsNavigator {

    //region - Properties

    private val viewModel: ArtistDetailsViewModel by viewModel()

    //endregion

    //region - Functions

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.displayableArtistAbout.observe(viewLifecycleOwner, Observer {
            onDisplayableChanged(it)
        })

        viewModel.navigator = WeakReference(this)

        return inflater.inflate(R.layout.fragment_artist_details_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsMoreInfoLinkButton.setOnClickListener {
            viewModel.handleTapOnMoreInfo()
        }
    }

    private fun onDisplayableChanged(displayableArtistAbout: DisplayableArtistAbout) {
        detailsGenderTextView.isVisible = displayableArtistAbout.shouldDisplayGender
        detailsGenderTextView.text = displayableArtistAbout.genderText

        detailsLifeSpanBeginLayout.isVisible = displayableArtistAbout.shouldDisplayLifeSpanBegin
        detailsLifeSpanBeginTitleTextView.text = displayableArtistAbout.lifeSpanBeginningTitleText
        detailsLifeSpanBeginTextView.text = displayableArtistAbout.lifeSpanBeginningText

        detailsLifeSpanEndLayout.isVisible = displayableArtistAbout.shouldDisplayLifeSpanEnd
        detailsLifeSpanEndTitleTextView.text = displayableArtistAbout.lifeSpanEndTitleText
        detailsLifeSpanEndTextView.text = displayableArtistAbout.lifeSpanEndText

        detailsCountryTextView.text = displayableArtistAbout.countryText

        detailsIpiCodesLayout.isVisible = displayableArtistAbout.shouldDisplayIpiCodes
        detailsIpiCodesTextView.text = displayableArtistAbout.ipiCodesText
        detailsIsniCodesLayout.isVisible = displayableArtistAbout.shouldDisplayIsniCodes
        detailsIsniCodesTextView.text = displayableArtistAbout.isniCodesText

        detailsRatingsTextView.text = displayableArtistAbout.ratingsText

        detailsWikipediaExtractTextView.text = displayableArtistAbout.wikipediaExtractText
        detailsMoreInfoLinkButton.isVisible = true
    }

    override fun openArtistWebSearch(artistNameQuery: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH)
        intent.putExtra(SearchManager.QUERY, artistNameQuery)
        activity?.startActivity(intent)
    }

    //endregion
}