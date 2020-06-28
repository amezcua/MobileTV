package net.byteabyte.mobiletv.showdetails.seasons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import net.byteabyte.mobiletv.core.tvshows.details.Season
import net.byteabyte.mobiletv.databinding.SeasonItemBinding

class SeasonsAdapter : ListAdapter<Season, SeasonViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder =
        SeasonViewHolder(
            SeasonItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        internal val diffCallback = object : DiffUtil.ItemCallback<Season>() {
            override fun areItemsTheSame(oldItem: Season, newItem: Season) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Season, newItem: Season) =
                oldItem == newItem
        }
    }
}