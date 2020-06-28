package net.byteabyte.mobiletv.showdetails.similar_shows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import net.byteabyte.mobiletv.ShowClickData
import net.byteabyte.mobiletv.core.tvshows.paged.ShowSummary
import net.byteabyte.mobiletv.databinding.SimilarShowItemBinding
import net.byteabyte.mobiletv.showSummaryDiffCallback

class SimilarShowsAdapter(private val onShowClick: (ShowClickData) -> Unit) :
    PagedListAdapter<ShowSummary, SimilarShowViewHolder>(showSummaryDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarShowViewHolder =
        SimilarShowViewHolder(
            SimilarShowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SimilarShowViewHolder, position: Int) {
        val show = getItem(position)
        if (show != null) {
            holder.bind(show, onShowClick)
        } else {
            holder.renderPlaceHolder()
        }
    }
}