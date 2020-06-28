package net.byteabyte.mobiletv.showdetails.similar_shows

import android.util.Pair
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.similar_show_item.view.*
import net.byteabyte.mobiletv.SharedTransitions
import net.byteabyte.mobiletv.ShowClickData
import net.byteabyte.mobiletv.core.tvshows.paged.ShowSummary
import net.byteabyte.mobiletv.databinding.SimilarShowItemBinding

class SimilarShowViewHolder(itemView: SimilarShowItemBinding) :
    RecyclerView.ViewHolder(itemView.root) {
    // TODO find a way to better display placeholders here.

    fun bind(showSummary: ShowSummary, onShowClick: (ShowClickData) -> Unit) {
        itemView.similarShowPosterView.render(showSummary)
        itemView.similarShowName.text = showSummary.name
        itemView.similarShowDescription.text = showSummary.description
        itemView.setOnClickListener {
            onShowClick(
                ShowClickData(
                    showSummary,
                    itemView.similarShowPosterView.displayedImageUrl,
                    SharedTransitions(
                        listOf(
                            Pair(itemView.similarShowPosterView as View, showSummary.id.toString())
                        )
                    )
                )
            )
        }
    }

    fun renderPlaceHolder() {

    }
}