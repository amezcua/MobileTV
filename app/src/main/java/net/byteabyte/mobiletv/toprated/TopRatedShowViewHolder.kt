package net.byteabyte.mobiletv.toprated

import android.util.Pair
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.show_backdrop.view.*
import kotlinx.android.synthetic.main.top_rated_show_item.view.*
import net.byteabyte.mobiletv.SharedTransitions
import net.byteabyte.mobiletv.core.tvshows.ImageUrl
import net.byteabyte.mobiletv.core.tvshows.top_rated.TopRatedShow
import net.byteabyte.mobiletv.databinding.TopRatedShowItemBinding
import net.byteabyte.mobiletv.uicomponents.ShowRating

class TopRatedShowViewHolder(itemView: TopRatedShowItemBinding) :
    RecyclerView.ViewHolder(itemView.root) {
    // TODO find a way to better display placeholders here.

    fun bind(topRatedShow: TopRatedShow, onShowClick: (TopRatedClickData) -> Unit) {
        itemView.showNameTextView.text = topRatedShow.name
        itemView.showBackdropView.render(topRatedShow)
        itemView.showRatingView.render(
            ShowRating.State(
                topRatedShow.rating,
                topRatedShow.maxRating,
                topRatedShow.totalVotes
            )
        )
        itemView.setOnClickListener {
            onShowClick(
                TopRatedClickData(
                    topRatedShow,
                    itemView.showBackdropView.displayedImageUrl,
                    SharedTransitions(
                        listOf(
                            Pair(itemView.backdropImageView as View, topRatedShow.id.toString())
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