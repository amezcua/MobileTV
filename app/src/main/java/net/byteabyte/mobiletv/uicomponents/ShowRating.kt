package net.byteabyte.mobiletv.uicomponents

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import net.byteabyte.mobiletv.databinding.ShowRatingBinding

class ShowRating @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val showRatingBinding: ShowRatingBinding =
        ShowRatingBinding.inflate(LayoutInflater.from(context), this, true)

    @SuppressLint("SetTextI18n")
    fun render(state: State) {
        showRatingBinding.ratingAverage.text = state.averageRating.toString()
        showRatingBinding.ratingMax.text = "/${state.maxRating}"
        showRatingBinding.ratingTotalVotes.text = state.totalVotes.toString()
    }

    data class State(val averageRating: Double, val maxRating: Int, val totalVotes: Int)
}