package net.byteabyte.mobiletv.core.tvshows.top_rated

import net.byteabyte.mobiletv.core.tvshows.ImagesMap
import net.byteabyte.mobiletv.core.tvshows.ShowId

data class TopRatedShow(
    val id: ShowId,
    val name: String,
    val backdropImages: ImagesMap,
    val posterImages: ImagesMap,
    val description: String,
    val rating: Double,
    val totalVotes: Int,
    val maxRating: Int = MAX_RATING_DEFAULT
) {
    companion object {
        const val MAX_RATING_DEFAULT = 10
    }
}