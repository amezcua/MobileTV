package net.byteabyte.mobiletv.uicomponents

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation
import net.byteabyte.mobiletv.core.tvshows.ImageUrl
import net.byteabyte.mobiletv.core.tvshows.ShowImagePicker
import net.byteabyte.mobiletv.core.tvshows.ShowImagePicker.PickImageResult
import net.byteabyte.mobiletv.core.tvshows.paged.ShowSummary
import net.byteabyte.mobiletv.databinding.ShowBackdropBinding
import javax.inject.Inject


@AndroidEntryPoint
class ShowBackdrop @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    @Inject
    lateinit var showImagePicker: ShowImagePicker

    var displayedImageUrl: ImageUrl? = null

    private val showBackdropBinding: ShowBackdropBinding =
        ShowBackdropBinding.inflate(LayoutInflater.from(context), this, true)

    fun render(showSummary: ShowSummary, onLoaded: () -> Unit = {}) {
        if (viewIsMeasured(showBackdropBinding.backdropImageView)) {
            loadImage(showSummary, onLoaded)
        } else {
            doOnLayout {
                loadImage(showSummary, onLoaded)
            }
        }
    }

    fun render(imageUrl: ImageUrl, onLoaded: () -> Unit = {}) {
        if (viewIsMeasured(showBackdropBinding.backdropImageView)) {
            loadImage(imageUrl, onLoaded)
        } else {
            doOnLayout {
                loadImage(imageUrl, onLoaded)
            }
        }
    }

    fun renderPlaceHolder() {
        // TODO
    }

    fun blur(onBlurred: () -> Unit) {
        val transformations =
            MultiTransformation(CenterCrop(), BlurTransformation(50, 3))
        Glide.with(this)
            .asBitmap()
            .load(displayedImageUrl)
            .placeholder(showBackdropBinding.backdropImageView.drawable)
            .transition(withCrossFade())
            .apply(RequestOptions.bitmapTransform(transformations))
            .addListener(GlideCallbackRequestListener(onBlurred))
            .into(showBackdropBinding.backdropImageView)
            .waitForLayout()

    }

    fun unBlur(onUnBlurred: () -> Unit) {
        Glide.with(this)
            .asBitmap()
            .load(displayedImageUrl)
            .placeholder(showBackdropBinding.backdropImageView.drawable)
            .centerCrop()
            .transition(withCrossFade())
            .addListener(GlideCallbackRequestListener(onUnBlurred))
            .into(showBackdropBinding.backdropImageView)
    }

    private fun loadImage(showSummary: ShowSummary, onLoaded: () -> Unit) {
        displayedImageUrl = pickBestBackdropImage(showSummary, showImagePicker)
        loadImage(displayedImageUrl, onLoaded)
    }

    private fun loadImage(imageUrl: ImageUrl?, onLoaded: () -> Unit) {
        displayedImageUrl = imageUrl
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .centerCrop()
            .listener(GlideCallbackRequestListener(onLoaded))
            .into(showBackdropBinding.backdropImageView)
    }

    private fun viewIsMeasured(view: ImageView): Boolean =
        view.measuredWidth != 0 && view.measuredHeight != 0

    private fun pickBestBackdropImage(showSummary: ShowSummary, showImagePicker: ShowImagePicker): ImageUrl? {
        if (this.measuredHeight == 0 && this.measuredHeight == 0) this.invalidate()

        val bestImage = showImagePicker.pickBestImage(
            listOf(showSummary.backdropImages, showSummary.posterImages),
            this.measuredWidth
        )
        return when (bestImage) {
            is PickImageResult.Placeholder -> null
            is PickImageResult.Image -> bestImage.url
        }
    }

    private class GlideCallbackRequestListener(private val callback: () -> Unit) :RequestListener<Bitmap> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Bitmap>?,
            isFirstResource: Boolean
        ): Boolean {
            callback()
            return false
        }

        override fun onResourceReady(
            resource: Bitmap?,
            model: Any?,
            target: Target<Bitmap>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            callback()
            return false
        }
    }
}