package net.byteabyte.mobiletv.uicomponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnLayout
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import net.byteabyte.mobiletv.core.tvshows.ImageUrl
import net.byteabyte.mobiletv.core.tvshows.Show
import net.byteabyte.mobiletv.core.tvshows.ShowImagePicker
import net.byteabyte.mobiletv.core.tvshows.ShowImagePicker.*
import net.byteabyte.mobiletv.databinding.ShowBackdropBinding
import javax.inject.Inject

@AndroidEntryPoint
class ShowBackdrop @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    @Inject
    lateinit var showImagePicker: ShowImagePicker

    private val showBackdropBinding: ShowBackdropBinding =
        ShowBackdropBinding.inflate(LayoutInflater.from(context), this, true)

    fun render(show: Show) {
        if (viewIsMeasured(showBackdropBinding.backdropImageView)) {
            loadImage(show)
        } else {
            doOnLayout {
                loadImage(show)
            }
        }
    }

    fun renderPlaceHolder() {
        // TODO
    }

    private fun loadImage(show: Show) {
        Glide.with(this)
            .load(pickBestBackdropImage(show, showImagePicker))
            .centerCrop()
            .into(showBackdropBinding.backdropImageView)
    }

    private fun viewIsMeasured(view: ImageView): Boolean =
        view.measuredWidth != 0 && view.measuredHeight != 0

    private fun pickBestBackdropImage(show: Show, showImagePicker: ShowImagePicker): ImageUrl? {
        if (this.measuredHeight == 0 && this.measuredHeight == 0) this.invalidate()

        val bestImage = showImagePicker.pickBestImage(
            show,
            Location.TOP_RATED_LIST_BG,
            this.measuredWidth
        )
        return when (bestImage) {
            is PickImageResult.Placeholder -> null
            is PickImageResult.Image -> bestImage.url
        }
    }
}