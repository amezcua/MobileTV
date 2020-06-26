package net.byteabyte.mobiletv.core.tvshows.details

import net.byteabyte.mobiletv.core.tvshows.EpisodeId
import net.byteabyte.mobiletv.core.tvshows.ImagesMap

data class Episode(
    val id: EpisodeId,
    val airDate: String,
    val episodeNumber: Int,
    val name: String,
    val description: String,
    val seasonNumber: Int,
    val stillImages: ImagesMap,
    val voteAverage: Double,
    val votesCount: Int
)