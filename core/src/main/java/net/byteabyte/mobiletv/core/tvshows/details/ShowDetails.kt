package net.byteabyte.mobiletv.core.tvshows.details

import net.byteabyte.mobiletv.core.tvshows.ImagesMap
import net.byteabyte.mobiletv.core.tvshows.ShowId

data class ShowDetails(
    val id: ShowId,
    val name: String,
    val backdropImages: ImagesMap,
    val authors: List<Author>,
    val firstAired: String, // TODO change to date
    val genres: List<Genre>,
    val homePageUrl: String,
    val inProduction: Boolean,
    val languages: List<String>,
    val lastEpisode: Episode?,
    val nextEpisode: Episode?,
    val networks: List<TVNetwork>,
    val episodesCount: Int,
    val originCountries: List<String>,
    val originalName: String,
    val description: String,
    val popularity: Double,
    val posterImages: ImagesMap,
    val productionCompanies: List<ProductionCompany>,
    val seasons: List<Season>,
    val status: String,
    val voteAverage: Double,
    val totalVotes: Int
)