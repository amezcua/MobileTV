package net.byteabyte.mobiletv.toprated

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_top_rated_shows.*
import net.byteabyte.mobiletv.R
import net.byteabyte.mobiletv.core.tvshows.Show

@AndroidEntryPoint
class TopRatedActivity : AppCompatActivity() {

    private val viewModel by viewModels<TopRatedViewModel>()
    private val showsAdapter = TopRatedAdapter(::onShowClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_rated_shows)

        configureRecyclerView()
        observeViewModel()
    }

    private fun configureRecyclerView() {
        topRatedShowsRecyclerView.adapter = showsAdapter
    }

    private fun observeViewModel() {
        viewModel.topRatedShows.observe(this, Observer { shows ->
            showsAdapter.submitList(shows)
        })
    }

    private fun onShowClick(show: Show) {
        // TODO
    }
}