package net.byteabyte.mobiletv.toprated

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import net.byteabyte.mobiletv.ShowClickData
import net.byteabyte.mobiletv.databinding.ActivityTopRatedShowsBinding
import net.byteabyte.mobiletv.showdetails.ShowDetailsActivity

@AndroidEntryPoint
class TopRatedActivity : AppCompatActivity() {

    private lateinit var topRatedBinding: ActivityTopRatedShowsBinding
    private val viewModel by viewModels<TopRatedViewModel>()
    private val showsAdapter = TopRatedAdapter(::onShowClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topRatedBinding = ActivityTopRatedShowsBinding.inflate(layoutInflater)
        setContentView(topRatedBinding.root)

        configureRecyclerView()
        observeViewModel()
    }

    private fun configureRecyclerView() {
        topRatedBinding.topRatedShowsRecyclerView.adapter = showsAdapter
        topRatedBinding.gotToTopButton.setOnClickListener {
            topRatedBinding.topRatedShowsRecyclerView.scrollToPosition(0)
        }
    }

    private fun observeViewModel() {
        viewModel.topRatedShows.observe(this, Observer { shows ->
            showsAdapter.submitList(shows)
        })

        viewModel.showLoading.observe(this, Observer { showLoading ->
            if (showLoading) {
                topRatedBinding.loadingOverlay.isVisible = true
            } else {
                hideLoading()
            }
        })
    }

    private fun hideLoading() {
        topRatedBinding.loadingOverlay.hide()
    }

    private fun onShowClick(topRatedClickData: ShowClickData) {
        ShowDetailsActivity.start(
            context = this,
            showId = topRatedClickData.showSummary.id,
            backdropUrl = topRatedClickData.displayedImage.orEmpty(),
            sharedTransitions = topRatedClickData.sharedTransitions
        )
    }
}