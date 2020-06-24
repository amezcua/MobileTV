package net.byteabyte.mobiletv.toprated

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import net.byteabyte.mobiletv.R
import net.byteabyte.mobiletv.core.tvshows.Show

@AndroidEntryPoint
class TopRatedActivity : AppCompatActivity() {

    private val viewModel by viewModels<TopRatedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.shows.observe(this, Observer { shows ->
            when (shows) {
                is TopRatedViewModel.TopRatedShowsState.ShowsAvailable -> renderShows(shows.shows)
                is TopRatedViewModel.TopRatedShowsState.Error -> renderShowsError()
            }
        })
    }

    private fun renderShows(shows: List<Show>) {
        Toast.makeText(this, "Got: $shows", Toast.LENGTH_SHORT).show()
    }

    private fun renderShowsError() {
        Toast.makeText(this, "Error retrieving shows", Toast.LENGTH_SHORT).show()
    }
}