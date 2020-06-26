package net.byteabyte.mobiletv.data.network.show_details

import net.byteabyte.mobiletv.core.tvshows.details.Season
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork

data class SeasonNetwork(
    val airDate: String, // TODO change to date
    val episodesCount: Int,
    val id: Int,
    val name: String,
    val description: String,
    val posterPath: String,
    val seasonNumber: Int
) {
    fun toSeason(imagesConfiguration: ImagesConfigurationNetwork) = Season(
        id = id,
        airDate = airDate,
        episodesCount = episodesCount,
        name = name,
        description = description,
        posterImages = imagesConfiguration.buildPosterImages(posterPath),
        seasonNumber = seasonNumber
    )
}