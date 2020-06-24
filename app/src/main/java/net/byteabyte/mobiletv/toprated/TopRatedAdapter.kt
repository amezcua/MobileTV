package net.byteabyte.mobiletv.toprated

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import net.byteabyte.mobiletv.R
import net.byteabyte.mobiletv.core.tvshows.Show

class TopRatedAdapter : PagedListAdapter<Show, TopRatedShowViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.top_rated_show_item, parent, false)
        return TopRatedShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopRatedShowViewHolder, position: Int) {
        val show = getItem(position)
        if (show != null) {
            holder.bind(show)
        } else {
            holder.renderPlaceholder()
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