package net.byteabyte.mobiletv.data.network.top_rated

import net.byteabyte.mobiletv.core.tvshows.top_rated.TopRatedShow
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork

internal data class TopRatedNetworkShow(
    val id: Int,
    val name: String,
    val backdropImage: String,
    val posterImage: String,
    val description: String,
    val rating: Double,
    val totalVotes: Int
) {
    fun toShow(imagesConfiguration: ImagesConfigurationNetwork): TopRatedShow {
        return TopRatedShow(
            id = id,
            name = name,
            backdropImages = imagesConfiguration.buildBackdropImages(backdropImage),
            posterImages = imagesConfiguration.buildPosterImages(posterImage),
            description = description,
            rating = rating,
            totalVotes = totalVotes
        )
    }
}