package net.byteabyte.mobiletv.core.tvshows.details

import net.byteabyte.mobiletv.core.tvshows.ImagesMap
import net.byteabyte.mobiletv.core.tvshows.SeasonId

data class Season(
    val id: SeasonId,
    val airDate: String,
    val episodesCount: Int,
    val name: String,
    val description: String,
    val posterImages: ImagesMap,
    val seasonNumber: Int
)