package net.byteabyte.mobiletv.showdetails

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearSnapHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_show_details.*
import net.byteabyte.mobiletv.R
import net.byteabyte.mobiletv.SharedTransitions
import net.byteabyte.mobiletv.ShowClickData
import net.byteabyte.mobiletv.core.tvshows.ImageUrl
import net.byteabyte.mobiletv.core.tvshows.ShowId
import net.byteabyte.mobiletv.core.tvshows.ShowImagePicker
import net.byteabyte.mobiletv.core.tvshows.details.Season
import net.byteabyte.mobiletv.core.tvshows.details.ShowDetails
import net.byteabyte.mobiletv.databinding.ActivityShowDetailsBinding
import net.byteabyte.mobiletv.extra
import net.byteabyte.mobiletv.showdetails.seasons.SeasonsAdapter
import net.byteabyte.mobiletv.showdetails.similar_shows.SimilarShowsAdapter
import net.byteabyte.mobiletv.uicomponents.ShowRating
import javax.inject.Inject


@AndroidEntryPoint
class ShowDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var showImagePicker: ShowImagePicker

    private val showId: ShowId by extra(EXTRA_SHOW_ID)
    private val backdropUrl: ImageUrl by extra(EXTRA_BACKDROP_IMAGE_URL)
    private val seasonsAdapter: SeasonsAdapter by lazy { SeasonsAdapter() }
    private val similarShowsAdapter: SimilarShowsAdapter by lazy { SimilarShowsAdapter(::onSimilarShowClick) }
    private lateinit var showDetailsBinding: ActivityShowDetailsBinding
    private val viewModel by viewModels<ShowDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showDetailsBinding = ActivityShowDetailsBinding.inflate(layoutInflater)
        setContentView(showDetailsBinding.root)

        supportPostponeEnterTransition()
        setupEnterTransition()
        configureSimilarShowsRecyclerView()
        setupBackButton()
    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()

        similarShowsRecyclerView.adapter = similarShowsAdapter
        seasonsRecyclerView.adapter = seasonsAdapter
        observeViewModel()
    }

    override fun onBackPressed() {
        goBack()
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        supportPostponeEnterTransition()
    }

    private fun setupEnterTransition() {
        ViewCompat.setTransitionName(showBackDropView, showId.toString())
        showBackDropView.render(backdropUrl) {
            scheduleStartPostponedTransition(showBackDropView)
        }
    }

    private fun setupBackButton() {
        gotBackButton.setOnClickListener {
            goBack()
        }
    }

    private fun goBack() {
        showBackDropView.unBlur {
            supportFinishAfterTransition()
        }
    }

    private fun observeViewModel() {
        observeShowDetails()
        viewModel.loadShow(showId)
        observeSimilarShows()
    }

    private fun observeShowDetails() {
        viewModel.showDetails.observe(this, Observer { showDetailsState ->
            when (showDetailsState) {
                is ShowDetailsViewModel.ShowDetailsState.ShowReady -> renderShow(showDetailsState.showDetails)
                is ShowDetailsViewModel.ShowDetailsState.ShowDetailsLoadError -> {
                    Toast.makeText(this, R.string.error_loading_show_details, LENGTH_SHORT).show()
                    supportFinishAfterTransition()
                }
            }
        })
    }

    private fun observeSimilarShows() {
        viewModel.similarShows.observe(this, Observer { similarShows ->
            similarShowsAdapter.submitList(similarShows)
        })
    }

    private fun renderShow(showDetails: ShowDetails) {
        showBackDropView.blur {
            gotBackButton.visibility = View.VISIBLE
            showPosterView.render(showDetails)
            showDetailsTitleTextView.text = showDetails.name
            showDetailsDescriptionTextView.text = showDetails.description
            renderRating(showDetails)
            renderSeasons(showDetails.seasons)
            similarShowsTitle.isVisible = true
            renderMoreInformation(showDetails)
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

    private fun renderSeasons(seasons: List<Season>) {
        seasonsTitle.isVisible = seasons.isNotEmpty()
        seasonsRecyclerView.isVisible = seasons.isNotEmpty()
        seasonsTitle.text = getString(R.string.seasons, seasons.size)
        seasonsAdapter.submitList(seasons)
    }

    private fun renderMoreInformation(showDetails: ShowDetails) {
        moreInformationTitle.isVisible = showDetails.homePageUrl.isNotBlank()
        moreInformationUrl.isVisible = showDetails.homePageUrl.isNotBlank()
        moreInformationUrl.text = showDetails.homePageUrl
        moreInformationUrl.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(showDetails.homePageUrl)))
        }
    }

    private fun configureSimilarShowsRecyclerView() {
        similarShowsRecyclerView.apply {
            LinearSnapHelper().attachToRecyclerView(this)
            adapter = similarShowsAdapter
        }
    }

    private fun onSimilarShowClick(show: ShowClickData) {
        showDetailsScrollView.smoothScrollTo(0, 0)
        viewModel.loadShow(show.showSummary.id)
    }

    private fun scheduleStartPostponedTransition(sharedElement: View) {
        sharedElement.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    sharedElement.viewTreeObserver.removeOnPreDrawListener(this)
                    supportStartPostponedEnterTransition()
                    return true
                }
            })
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