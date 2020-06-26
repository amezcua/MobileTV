package net.byteabyte.mobiletv.showdetails

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import dagger.hilt.android.AndroidEntryPoint
import net.byteabyte.mobiletv.R
import net.byteabyte.mobiletv.SharedTransitions
import net.byteabyte.mobiletv.core.tvshows.ImageUrl
import net.byteabyte.mobiletv.core.tvshows.ShowId
import net.byteabyte.mobiletv.databinding.ActivityShowDetailsBinding
import net.byteabyte.mobiletv.extra

@AndroidEntryPoint
class ShowDetailsActivity : AppCompatActivity() {

    private val showId: ShowId by extra(EXTRA_SHOW_ID)
    private val backdropUrl: ImageUrl by extra(EXTRA_BACKDROP_IMAGE_URL)
    private lateinit var showDetailsBinding: ActivityShowDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showDetailsBinding = ActivityShowDetailsBinding.inflate(layoutInflater)
        setContentView(showDetailsBinding.root)

        supportPostponeEnterTransition()
        ViewCompat.setTransitionName(findViewById(R.id.showBackDropView), showId.toString())
        showDetailsBinding.showBackDropView.render(backdropUrl)
    }

    override fun onResume() {
        super.onResume()

        supportStartPostponedEnterTransition()
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