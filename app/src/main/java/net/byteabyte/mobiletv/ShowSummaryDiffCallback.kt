package net.byteabyte.mobiletv

import androidx.recyclerview.widget.DiffUtil
import net.byteabyte.mobiletv.core.tvshows.paged.ShowSummary

internal val showSummaryDiffCallback = object : DiffUtil.ItemCallback<ShowSummary>() {
    override fun areItemsTheSame(oldItem: ShowSummary, newItem: ShowSummary) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ShowSummary, newItem: ShowSummary) =
        oldItem == newItem
}