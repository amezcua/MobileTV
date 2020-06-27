package net.byteabyte.mobiletv.uicomponents

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnRepeat
import androidx.core.view.isVisible
import net.byteabyte.mobiletv.databinding.LoadingOverlayBinding

class LoadingOverlay @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var pulses = 0
    private var cancelled = false

    private val pulseAnimation: ObjectAnimator by lazy {
        pulse(loadingOverlayBinding.loadingTextView) {
            fadeOut()
        }
    }

    private val loadingOverlayBinding: LoadingOverlayBinding =
        LoadingOverlayBinding.inflate(LayoutInflater.from(context), this, true)

    override fun onFinishInflate() {
        super.onFinishInflate()

        pulseText()
    }

    fun hide() {
        cancelled = true
    }

    private fun fadeOut() {
        ObjectAnimator.ofFloat(this, "alpha", 0.0f).apply {
            duration = FADE_OUT_DURATION
            doOnEnd {
                isVisible = false
            }
        }.start()
    }

    private fun pulseText() {
        pulseAnimation.start()
    }

    private fun pulse(view: View, onFinish: () -> Unit): ObjectAnimator =
        ObjectAnimator.ofFloat(view, "alpha", 0.0f).apply {
            duration = PULSE_DURATION
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            doOnRepeat {
                if (cancelled && pulses >= MINIMUM_PULSE_COUNT) {
                    pulseAnimation.cancel()
                    onFinish()
                }
                pulses += 1
            }
        }

    companion object {
        private const val FADE_OUT_DURATION = 250L
        private const val MINIMUM_PULSE_COUNT = 2
        private const val PULSE_DURATION = 1000L
    }
}