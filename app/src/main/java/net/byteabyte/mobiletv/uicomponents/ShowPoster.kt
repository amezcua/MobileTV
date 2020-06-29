package net.byteabyte.mobiletv.uicomponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.show_poster.view.*
import net.byteabyte.mobiletv.core.tvshows.ImageUrl
import net.byteabyte.mobiletv.core.tvshows.ImagesMap
import net.byteabyte.mobiletv.core.tvshows.ShowImagePicker
import net.byteabyte.mobiletv.core.tvshows.ShowImagePicker.PickImageResult
import net.byteabyte.mobiletv.core.tvshows.details.Season
import net.byteabyte.mobiletv.core.tvshows.details.ShowDetails
import net.byteabyte.mobiletv.core.tvshows.paged.ShowSummary
import net.byteabyte.mobiletv.databinding.ShowPosterBinding
import javax.inject.Inject

@AndroidEntryPoint
class ShowPoster @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    @Inject
    lateinit var showImagePicker: ShowImagePicker

    var displayedImageUrl: ImageUrl? = null

    private val showPosterBinding: ShowPosterBinding =
        ShowPosterBinding.inflate(LayoutInflater.from(context), this, true)

    fun render(showDetails: ShowDetails) {
        if (viewIsMeasured(showPosterBinding.posterImageView)) {
            loadImage(showDetails)
        } else {
            doOnLayout {
                loadImage(showDetails)
            }
        }
    }

    fun render(showSummary: ShowSummary) {
        if (viewIsMeasured(showPosterBinding.posterImageView)) {
            loadImage(showSummary)
        } else {
            doOnLayout {
                loadImage(showSummary)
            }
        }
    }

    fun render(season: Season) {
        if (viewIsMeasured(showPosterBinding.posterImageView)) {
            loadImage(season)
        } else {
            doOnLayout {
                loadImage(season)
            }
        }
    }

    private fun loadImage(showDetails: ShowDetails) {
        loadImage(
            pickBestPosterImage(
                listOf(showDetails.posterImages, showDetails.backdropImages),
                showImagePicker
            )
        )
    }

    private fun loadImage(showSummary: ShowSummary) {
        loadImage(
            pickBestPosterImage(
                listOf(showSummary.posterImages, showSummary.backdropImages),
                showImagePicker
            )
        )
    }

    private fun loadImage(season: Season) {
        loadImage(
            pickBestPosterImage(listOf(season.posterImages), showImagePicker)
        )
    }

    private fun loadImage(imageUrl: ImageUrl?) {
        displayedImageUrl = imageUrl
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .placeholder(posterImageView.drawable)
            .centerCrop()
            .transition(withCrossFade())
            .into(showPosterBinding.posterImageView)
    }

    private fun viewIsMeasured(view: ImageView): Boolean =
        view.measuredWidth != 0 && view.measuredHeight != 0

    private fun pickBestPosterImage(
        imageMaps: List<ImagesMap>,
        showImagePicker: ShowImagePicker
    ): ImageUrl? {
        if (this.measuredHeight == 0 && this.measuredHeight == 0) this.invalidate()

        return when (val bestImage = showImagePicker.pickBestImage(imageMaps, this.measuredWidth)) {
            is PickImageResult.Placeholder -> null
            is PickImageResult.Image -> bestImage.url
        }
    }
}