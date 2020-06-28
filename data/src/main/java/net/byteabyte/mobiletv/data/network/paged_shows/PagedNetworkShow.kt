package net.byteabyte.mobiletv.data.network.paged_shows

import net.byteabyte.mobiletv.core.tvshows.paged.ShowSummary
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork

internal data class PagedNetworkShow(
    val id: Int,
    val name: String,
    val backdropImage: String,
    val posterImage: String,
    val description: String,
    val rating: Double,
    val totalVotes: Int
) {
    fun toShow(imagesConfiguration: ImagesConfigurationNetwork): ShowSummary {
        return ShowSummary(
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