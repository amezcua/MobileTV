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
import net.byteabyte.mobiletv.core.tvshows.ImageUrl
import net.byteabyte.mobiletv.core.tvshows.ShowImagePicker
import net.byteabyte.mobiletv.core.tvshows.ShowImagePicker.PickImageResult
import net.byteabyte.mobiletv.core.tvshows.details.ShowDetails
import net.byteabyte.mobiletv.databinding.ShowPosterBinding
import javax.inject.Inject

@AndroidEntryPoint
class ShowPoster @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    @Inject
    lateinit var showImagePicker: ShowImagePicker

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

    private fun loadImage(showDetails: ShowDetails) {
        loadImage(pickBestPosterImage(showDetails, showImagePicker))
    }

    private fun loadImage(imageUrl: ImageUrl?) {
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .centerCrop()
            .transition(withCrossFade())
            .into(showPosterBinding.posterImageView)
    }

    private fun viewIsMeasured(view: ImageView): Boolean =
        view.measuredWidth != 0 && view.measuredHeight != 0

    private fun pickBestPosterImage(showDetails: ShowDetails, showImagePicker: ShowImagePicker): ImageUrl? {
        if (this.measuredHeight == 0 && this.measuredHeight == 0) this.invalidate()

        val bestImage = showImagePicker.pickBestImage(
            listOf(showDetails.posterImages, showDetails.backdropImages),
            this.measuredWidth
        )
        return when (bestImage) {
            is PickImageResult.Placeholder -> null
            is PickImageResult.Image -> bestImage.url
        }
    }
}