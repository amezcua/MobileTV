package net.byteabyte.mobiletv.data.network.top_rated

import net.byteabyte.mobiletv.core.tvshows.Show

data class TopRatedTvShow(
    val id: Int,
    val name: String,
    val backdropImage: String,
    val posterImage: String,
    val description: String,
    val rating: Double,
    val totalVotes: Int
) {
    fun toShow(): Show {
        return Show(
            id = id,
            name = name,
            backdropImage = backdropImage,
            posterImage = posterImage,
            description = description,
            rating = rating,
            totalVotes = totalVotes
        )
    }
}