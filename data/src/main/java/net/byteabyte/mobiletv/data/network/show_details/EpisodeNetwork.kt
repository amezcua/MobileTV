package net.byteabyte.mobiletv.data.network.show_details

import net.byteabyte.mobiletv.core.tvshows.details.Episode
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork

data class EpisodeNetwork(
    val airDate: String, // TODO change to date
    val episodeNumber: Int,
    val id: Int,
    val name: String,
    val description: String,
    val seasonNumber: Int,
    val showId: Int,
    val stillPath: String,
    val voteAverage: Double,
    val voteCount: Int
) {
    fun toEpisode(imagesConfiguration: ImagesConfigurationNetwork) = Episode(
        id = id,
        airDate = airDate,
        episodeNumber = episodeNumber,
        name = name,
        description = description,
        seasonNumber = seasonNumber,
        stillImages = imagesConfiguration.buildStillImages(stillPath),
        voteAverage = voteAverage,
        votesCount = voteCount
    )
}