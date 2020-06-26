package net.byteabyte.mobiletv.toprated

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import net.byteabyte.mobiletv.SharedTransitions
import net.byteabyte.mobiletv.core.tvshows.ImageUrl
import net.byteabyte.mobiletv.core.tvshows.top_rated.TopRatedShow
import net.byteabyte.mobiletv.databinding.TopRatedShowItemBinding

class TopRatedAdapter(private val onShowClick: (TopRatedClickData) -> Unit) :
    PagedListAdapter<TopRatedShow, TopRatedShowViewHolder>(DIFF_CALLBACK) {
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

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TopRatedShow>() {
            override fun areItemsTheSame(oldItem: TopRatedShow, newItem: TopRatedShow) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TopRatedShow, newItem: TopRatedShow) =
                oldItem == newItem
        }
    }
}