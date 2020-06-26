package net.byteabyte.mobiletv.toprated

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.top_rated_show_item.view.*
import net.byteabyte.mobiletv.core.tvshows.Show
import net.byteabyte.mobiletv.databinding.TopRatedShowItemBinding

class TopRatedShowViewHolder(itemView: TopRatedShowItemBinding): RecyclerView.ViewHolder(itemView.root) {
    // TODO find a way to better display placeholders here.

    fun bind(show: Show, onShowClick: (Show) -> Unit) {
        itemView.showNameTextView.text = show.name
        itemView.showBackdropView.render(show)
        itemView.setOnClickListener { onShowClick(show) }
    }

    fun renderPlaceHolder() {
        itemView.showNameTextView.text = ""
        itemView.showBackdropView.renderPlaceHolder()
    }
}