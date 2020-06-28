package net.byteabyte.mobiletv.showdetails

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.transition.doOnEnd
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_show_details.*
import net.byteabyte.mobiletv.R
import net.byteabyte.mobiletv.SharedTransitions
import net.byteabyte.mobiletv.core.tvshows.ImageUrl
import net.byteabyte.mobiletv.core.tvshows.ShowId
import net.byteabyte.mobiletv.core.tvshows.details.ShowDetails
import net.byteabyte.mobiletv.databinding.ActivityShowDetailsBinding
import net.byteabyte.mobiletv.extra
import net.byteabyte.mobiletv.uicomponents.ShowRating

@AndroidEntryPoint
class ShowDetailsActivity : AppCompatActivity() {

    private val showId: ShowId by extra(EXTRA_SHOW_ID)
    private val backdropUrl: ImageUrl by extra(EXTRA_BACKDROP_IMAGE_URL)
    private lateinit var showDetailsBinding: ActivityShowDetailsBinding
    private val viewModel by viewModels<ShowDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showDetailsBinding = ActivityShowDetailsBinding.inflate(layoutInflater)
        setContentView(showDetailsBinding.root)

        setupEnterTransition()
        setupBackButton()
        observeViewModel()
    }

    private fun setupEnterTransition() {
        ViewCompat.setTransitionName(findViewById(R.id.showBackDropView), showId.toString())
        showDetailsBinding.showBackDropView.render(backdropUrl)
    }

    private fun setupBackButton() {
        gotBackButton.setOnClickListener {
            supportFinishAfterTransition()
        }
    }

    private fun observeViewModel() {
        viewModel.showDetails.observe(this, Observer { showDetailsState ->
            when (showDetailsState) {
                is ShowDetailsViewModel.ShowDetailsState.ShowReady -> renderShow(showDetailsState.showDetails)
                is ShowDetailsViewModel.ShowDetailsState.ShowDetailsLoadError -> {
                    Toast.makeText(this, R.string.error_loading_show_details, Toast.LENGTH_SHORT)
                        .show()
                    supportFinishAfterTransition()
                }
            }
        })

        window.sharedElementEnterTransition.doOnEnd {
            viewModel.loadShow(showId)
        }
    }

    private fun renderShow(showDetails: ShowDetails) {
        showBackDropView.blur {
            gotBackButton.visibility = View.VISIBLE
            showPosterView.render(showDetails)
            showDetailsTitleTextView.text = showDetails.name
            showDetailsDescriptionTextView.text = showDetails.description
            renderRating(showDetails)
        }
    }

    private fun renderRating(showDetails: ShowDetails) {
        showRatingView.render(
            ShowRating.State(
                showDetails.voteAverage,
                10,
                showDetails.totalVotes
            )
        ).apply { isVisible = true }
    }

    companion object {
        private const val EXTRA_SHOW_ID = "EXTRA_SHOW_ID"
        private const val EXTRA_BACKDROP_IMAGE_URL = "EXTRA_BACKDROP_IMAGE_URL"

        fun start(
            context: Activity,
            showId: ShowId,
            backdropUrl: ImageUrl,
            sharedTransitions: SharedTransitions
        ) {
            val transitionsArray = sharedTransitions.items.toTypedArray()
            val options = ActivityOptions.makeSceneTransitionAnimation(context, *transitionsArray)
            val intent = Intent(context, ShowDetailsActivity::class.java).apply {
                putExtra(EXTRA_SHOW_ID, showId)
                putExtra(EXTRA_BACKDROP_IMAGE_URL, backdropUrl)
            }
            context.startActivity(intent, options.toBundle())
        }
    }
}