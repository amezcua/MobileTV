package net.byteabyte.mobiletv.toprated

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import net.byteabyte.mobiletv.ShowClickData
import net.byteabyte.mobiletv.showSummaryDiffCallback
import net.byteabyte.mobiletv.core.tvshows.paged.ShowSummary
import net.byteabyte.mobiletv.databinding.TopRatedShowItemBinding

class TopRatedAdapter(private val onShowClick: (ShowClickData) -> Unit) :
    PagedListAdapter<ShowSummary, TopRatedShowViewHolder>(showSummaryDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedShowViewHolder =
        TopRatedShowViewHolder(
            TopRatedShowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TopRatedShowViewHolder, position: Int) {
        val show = getItem(position)
        if (show != null) {
            holder.bind(show, onShowClick)
        } else {
            holder.renderPlaceHolder()
        }
    }
}