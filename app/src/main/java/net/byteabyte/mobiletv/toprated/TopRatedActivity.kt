package net.byteabyte.mobiletv.toprated

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
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
    }

    private fun onShowClick(topRatedClickData: TopRatedClickData) {
        ShowDetailsActivity.start(
            context = this,
            showId = topRatedClickData.topRatedShow.id,
            backdropUrl = topRatedClickData.topRatedDisplayedImage.orEmpty(),
            sharedTransitions = topRatedClickData.sharedTransitions
        )
    }
}