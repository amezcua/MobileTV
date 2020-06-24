package net.byteabyte.mobiletv.toprated

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import net.byteabyte.mobiletv.R
import net.byteabyte.mobiletv.core.tvshows.Show

class TopRatedShowViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    // TODO find a way to better display placeholders here.
    fun bind(show: Show) {
        val showName = itemView.findViewById<TextView>(R.id.showNameTextView)
        showName.text = show.name
        itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, android.R.color.transparent))
    }

    fun renderPlaceholder() {
        val showName = itemView.findViewById<TextView>(R.id.showNameTextView)
        showName.text = ""
        itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
    }
}