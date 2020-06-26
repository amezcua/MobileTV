package net.byteabyte.mobiletv.data.network.show_details

import net.byteabyte.mobiletv.core.tvshows.details.ShowDetails
import net.byteabyte.mobiletv.data.network.imagesconfiguration.ImagesConfigurationNetwork

data class ShowDetailsNetwork(
    val id: Int,
    val name: String,
    val backdropPath: String,
    val authors: List<ShowAuthorNetwork>,
    val runTimes: List<Int>,
    val firstAirDate: String, // TODO change to date
    val genres: List<GenreNetwork>,
    val homePageUrl: String,
    val inProduction: Boolean,
    val languages: List<String>,
    val lastEpisode: EpisodeNetwork?,
    val nextEpisode: EpisodeNetwork?,
    val networks: List<NetworkNetwork>,
    val episodesCount: Int,
    val originCountries: List<String>,
    val originalName: String,
    val description: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompanyNetwork>,
    val seasons: List<SeasonNetwork>,
    val status: String,
    val voteAverage: Double,
    val totalVotes: Int
) {
    // Basic validation after being parsed. We may want to add additional validations depending
    // on what we consider the minimal data that we want to display
    fun isValid(): Boolean = id > 0 && name.isNotBlank()

    fun toShowDetails(imagesConfiguration: ImagesConfigurationNetwork) = ShowDetails(
        id = id,
        name = name,
        backdropImages = imagesConfiguration.buildBackdropImages(backdropPath),
        authors = authors.map { it.toAuthor(imagesConfiguration) },
        firstAired = firstAirDate,
        genres = genres.map { it.toGenre() },
        homePageUrl = homePageUrl,
        inProduction = inProduction,
        languages = languages,
        lastEpisode = lastEpisode?.toEpisode(imagesConfiguration),
        nextEpisode = nextEpisode?.toEpisode(imagesConfiguration),
        episodesCount = episodesCount,
        networks = networks.map { it.toTVNetwork(imagesConfiguration) },
        originCountries = originCountries,
        originalName = originalName,
        description = description,
        popularity = popularity,
        posterImages = imagesConfiguration.buildPosterImages(posterPath),
        productionCompanies = productionCompanies.map { it.toProductionCompany(imagesConfiguration) },
        seasons = seasons.map { it.toSeason(imagesConfiguration) },
        status = status,
        voteAverage = voteAverage,
        totalVotes = totalVotes
    )
}