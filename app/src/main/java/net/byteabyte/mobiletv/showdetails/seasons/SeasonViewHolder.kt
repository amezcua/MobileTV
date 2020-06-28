package net.byteabyte.mobiletv.showdetails.seasons

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.season_item.view.*
import net.byteabyte.mobiletv.R
import net.byteabyte.mobiletv.core.tvshows.details.Season
import net.byteabyte.mobiletv.databinding.SeasonItemBinding

class SeasonViewHolder(itemView: SeasonItemBinding) :
    RecyclerView.ViewHolder(itemView.root) {
    fun bind(season: Season) {
        itemView.seasonPosterView.render(season)
        itemView.seasonName.text = season.name
        itemView.seasonEpisodeCount.text = itemView.context.getString(R.string.episodes, season.episodesCount)
        itemView.seasonDescription.isVisible = season.description.isNotBlank()
        itemView.seasonDescription.text = season.description
    }
}