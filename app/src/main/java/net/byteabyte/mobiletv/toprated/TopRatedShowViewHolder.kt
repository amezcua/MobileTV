package net.byteabyte.mobiletv.toprated

import android.util.Pair
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.show_backdrop.view.*
import kotlinx.android.synthetic.main.top_rated_show_item.view.*
import net.byteabyte.mobiletv.SharedTransitions
import net.byteabyte.mobiletv.ShowClickData
import net.byteabyte.mobiletv.core.tvshows.paged.ShowSummary
import net.byteabyte.mobiletv.databinding.TopRatedShowItemBinding
import net.byteabyte.mobiletv.uicomponents.ShowRating

class TopRatedShowViewHolder(itemView: TopRatedShowItemBinding) :
    RecyclerView.ViewHolder(itemView.root) {
    // TODO find a way to better display placeholders here.

    fun bind(showSummary: ShowSummary, onShowClick: (ShowClickData) -> Unit) {
        itemView.showNameTextView.text = showSummary.name
        itemView.showBackdropView.render(showSummary)
        itemView.showRatingView.render(
            ShowRating.State(
                showSummary.rating,
                showSummary.maxRating,
                showSummary.totalVotes
            )
        )
        itemView.setOnClickListener {
            onShowClick(
                ShowClickData(
                    showSummary,
                    itemView.showBackdropView.displayedImageUrl,
                    SharedTransitions(
                        listOf(
                            Pair(itemView.backdropImageView as View, showSummary.id.toString())
                        )
                    )
                )
            )
        }
    }

    fun renderPlaceHolder() {
        itemView.showNameTextView.text = ""
        itemView.showBackdropView.renderPlaceHolder()
    }
}