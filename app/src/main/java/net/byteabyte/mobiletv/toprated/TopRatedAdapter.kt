package net.byteabyte.mobiletv.toprated

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import net.byteabyte.mobiletv.core.tvshows.Show
import net.byteabyte.mobiletv.databinding.TopRatedShowItemBinding

class TopRatedAdapter(private val onShowClick: (Show) -> Unit) : PagedListAdapter<Show, TopRatedShowViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedShowViewHolder =
        TopRatedShowViewHolder(
            TopRatedShowItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))

    override fun onBindViewHolder(holder: TopRatedShowViewHolder, position: Int) {
        val show = getItem(position)
        if (show != null) {
            holder.bind(show, onShowClick)
        } else {
            holder.renderPlaceHolder()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Show>() {
            override fun areItemsTheSame(oldItem: Show, newItem: Show) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Show, newItem: Show) =
                oldItem == newItem
        }
    }
}